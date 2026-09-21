import java.util.ArrayList;

/**
 * Solucionador y simulador del concurso para SlotMachine.
 * Implementa la resolución a ciegas y la animación paso a paso en el Canvas.
 * 
 * @author De La Peña - Latorre
 * @version 1.0 (2026)
 */
public class SlotMachineContest {

    /** Máximo de acciones (giros) permitido por la maratón. */
    public static final int MAX_ACTIONS = 10000;

    /**
     * Mayor n que se simula: cabe en la ventana gráfica y la animación
     * dura un tiempo razonable.
     */
    public static final int MAX_SIMULATION_SIZE = 8;

    /**
     * Máquina usada en el último solve. Al terminar solve queda en jackpot;
     * simulate la reutiliza para mostrar la solución gráficamente.
     */
    static SlotMachine machine;

    /**
     * Soluciona la máquina tragamonedas encontrando y ejecutando la secuencia
     * de giros para alcanzar el jackpot.
     *
     * @param n Cantidad de ruedas y símbolos.
     * @return Matriz con la secuencia de acciones [rueda, pasos].
     */
    public static int[][] solve(int n) {
        if (n < 2 || n > 15) {
            return new int[0][2];
        }

        try {
            machine = new SlotMachine(n);
        } catch (Exceptions.InvalidSymbolsQuantException e) {
            return new int[0][2];
        }

        ArrayList<int[]> actions = new ArrayList<int[]>();

        // Fase 1: Alineación de cada rueda individualmente
        for (int wheel = 1; wheel <= n; wheel++) {
            int best = machine.distinctSymbols();
            int bestOffset = 0;
            for (int offset = 1; offset < n; offset++) {
                machine.spin(wheel, 1);
                actions.add(new int[]{wheel, 1});
                int count = machine.distinctSymbols();
                if (count > best) {
                    best = count;
                    bestOffset = offset;
                }
            }
            int back = (bestOffset + 1) % n;
            if (back != 0) {
                machine.spin(wheel, back);
                actions.add(new int[]{wheel, back});
            }
        }

        // Fase 2: Sincronización con la rueda 1
        machine.spin(1, 1);
        actions.add(new int[]{1, 1});

        int[] offsets = new int[n + 1];
        for (int wheel = 2; wheel <= n; wheel++) {
            int best = -1;
            for (int offset = 1; offset < n; offset++) {
                machine.spin(wheel, 1);
                actions.add(new int[]{wheel, 1});
                int count = machine.distinctSymbols();
                if (count > best) {
                    best = count;
                    offsets[wheel] = offset;
                }
            }
            machine.spin(wheel, 1);
            actions.add(new int[]{wheel, 1});
        }

        for (int wheel = 2; wheel <= n; wheel++) {
            if (offsets[wheel] > 0) {
                machine.spin(wheel, offsets[wheel]);
                actions.add(new int[]{wheel, offsets[wheel]});
            }
        }

        machine.spin(1, n - 1);
        actions.add(new int[]{1, n - 1});

        return actions.toArray(new int[0][]);
    }

    /**
     * Simula gráficamente en el Canvas los pasos necesarios para que la máquina
     * alcance el Jackpot.
     *
     * @param n Cantidad de ruedas y símbolos.
     */
    public static void simulate(int n) {
        if (n < 2 || n > MAX_SIMULATION_SIZE) {
            return;
        }

        // Ejecuta solve de forma invisible para calcular las acciones
        int[][] actions = solve(n);
        if (actions.length == 0 || machine == null) {
            return;
        }

        // Rebobina la máquina a su estado inicial antes de hacerla visible
        for (int k = actions.length - 1; k >= 0; k--) {
            int stepsBack = (n - (actions[k][1] % n)) % n;
            if (stepsBack > 0) {
                machine.spin(actions[k][0], stepsBack);
            }
        }

        // Hace visible la máquina en el Canvas
        machine.makeVisible();

        // Ejecuta y anima los giros optimizados por rueda
        int k = 0;
        while (k < actions.length) {
            int wheel = actions[k][0];
            int total = 0;
            while (k < actions.length && actions[k][0] == wheel) {
                total += actions[k][1];
                k++;
            }
            total = (total % n + n) % n; // Asegura giros positivos válidos para SlotMachine
            if (total != 0) {
                machine.spin(wheel, total);
            }
        }
    }
}
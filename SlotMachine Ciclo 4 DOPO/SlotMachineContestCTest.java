import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

/**
 * Pruebas unitarias compartidas para SlotMachine y SlotMachineContest.
 * 
 * @author De La Peña - Latorre
 * @version 1.0 (2026)
 */
public class SlotMachineContestCTest {

            // =========================================================================
        // GRUPO: GomezB - CarreroC
        // =========================================================================

        /**
         * Verifica que solve devuelva una cantidad válida de movimientos
         * dentro del rango permitido por la maratón.
         */
        @Test
        public void accordingCcGbShouldtestSolveNumberOfMovements() {
            SlotMachineContest contest = new SlotMachineContest();
            int n = 5;
            int[][] solution = contest.solve(n);

            assertNotNull("La solución no debe ser nula", solution);
            assertTrue("Debe retornar acciones", solution.length > 0);
            assertTrue("No debe superar el límite de acciones", 
                    solution.length <= SlotMachineContest.MAX_ACTIONS);
        }

        /**
         * Verifica que al crear una máquina de 3 ruedas, cada rueda se inicialice
         * con el símbolo correspondiente de la paleta.
         */
        @Test
        public void accordingCcGbShouldtestAllWheelsHaveSameSymbolOrder() {
            try {
                SlotMachine machine = new SlotMachine(3);
                ArrayList<String> configuration = machine.configuration();

                assertEquals("La máquina debe tener 3 ruedas", 3, configuration.size());
                assertEquals("red", configuration.get(0));
                assertEquals("blue", configuration.get(1));
                assertEquals("green", configuration.get(2));
            } catch (Exceptions.InvalidSymbolsQuantException e) {
                fail("No debería lanzar excepción para n = 3: " + e.getMessage());
            }
        }

        // =========================================================================
        // GRUPO: CortazarJ - MartinezC
        // =========================================================================

        /**
         * Verifica que SlotMachine(n) cree exactamente n ruedas y registre
         * la cantidad adecuada de símbolos en la máquina.
         */
        @Test
        public void accordingCjMcShouldCreateEqualWheelsAndSymbolsPerWheel() {
            try {
                int n = 5;
                SlotMachine sm = new SlotMachine(n);

                // En SlotMachine.java, configuration() y symbols() retornan ArrayList<String>
                assertEquals("Debe crear exactamente n ruedas", n, sm.configuration().size());
                assertEquals("Debe registrar n símbolos en la máquina", n, sm.symbols().size());
            } catch (Exceptions.InvalidSymbolsQuantException e) {
                fail("No debería lanzar excepción para n = 5: " + e.getMessage());
            }
        }

        /**
         * Verifica que cada acción propuesta por solve(n) referencie una rueda
         * dentro de los rangos válidos de la máquina (1 a n).
         */
        @Test
        public void accordingCjMcShouldProposeAtMostOneActionPerValidWheel() {
            int n = 4;
            int[][] actions = SlotMachineContest.solve(n);

            assertNotNull("La solución no debe ser nula", actions);
            for (int[] action : actions) {
                int wheel = action[0];
                assertTrue("La rueda (" + wheel + ") debe estar en el rango [1, " + n + "]", 
                        wheel >= 1 && wheel <= n);
            }
        }

        // =========================================================================
        // GRUPO 6: CañonA - PaezP
        // =========================================================================

        /**
         * Verifica que la estructura de los movimientos retornados por solve sea
         * una matriz con tuplas de longitud 2 [rueda, pasos].
         */
        @Test
        public void accordingCaPpshouldReturnValidMovesStructure() {
            SlotMachineContest contest = new SlotMachineContest();
            int n = 3;
            int[][] moves = contest.solve(n);

            assertNotNull("La matriz de movimientos no debe ser null", moves);
            for (int[] move : moves) {
                assertEquals("Cada movimiento debe ser [rueda, pasos] (longitud 2)", 2, move.length);
            }
        }

        /**
         * Verifica que el método simulate(n) se ejecute sin arrojar excepciones.
         */
        @Test
        public void accordingCaPpshouldRunSimulationWithoutErrors() {
            int n = 3;
            SlotMachineContest contest = new SlotMachineContest();
            try {
                contest.simulate(n);
                assertTrue("simulate se ejecutó correctamente", true);
            } catch (Exception e) {
                fail("simulate no debe lanzar excepciones: " + e.getMessage());
            }
        }

        // =========================================================================
        // GRUPO 5: BustosL - GomezG
        // =========================================================================

        /**
         * Verifica que ninguna acción de la solución tenga un número de pasos negativo.
         */
        @Test
        public void accordingSharedShouldNotHaveNegativeSteps() {
            int n = 5;
            int[][] solution = SlotMachineContest.solve(n);

            assertNotNull("La solución no debe ser null", solution);
            for (int[] move : solution) {
                assertTrue("El número de pasos no puede ser negativo", move[1] >= 0);
            }
        }

        /**
         * Verifica el manejo de casos límite cuando n es menor a 2.
         */
        @Test
        public void accordingSharedShouldSolveOneWheelMachine() {
            int n = 1;
            int[][] solution = SlotMachineContest.solve(n);

            assertNotNull("La solución no debe ser null", solution);
            assertEquals("Para n = 1 no se requieren giros (ya está en jackpot)", 0, solution.length);
        }

        /**
         * Verifica que para cada acción [rueda, pasos] retornada por solve(n):
         * 1. La solución no sea nula y contenga movimientos válidos.
         * 2. No exceda el número máximo de acciones permitido por la maratón (MAX_ACTIONS).
         * 3. La rueda referenciada esté dentro del rango válido [1, n].
         * 4. Los pasos de giro sean estrictamente positivos y menores a una vuelta completa (0 < pasos < n).
         */
        @Test
        public void accordingDpLaShouldHaveStepsWithinValidWheelCycle() {
            int n = 4;
            
            // Invocación estática directa acorde con la implementación adaptada
            int[][] solution = SlotMachineContest.solve(n);
    
            assertNotNull("La solución no debe ser null", solution);
            assertTrue("La solución debe contener acciones", solution.length > 0);
            assertTrue("La cantidad de acciones (" + solution.length + ") no debe superar el límite de la maratón", 
                       solution.length <= SlotMachineContest.MAX_ACTIONS);
            
            for (int[] action : solution) {
                assertEquals("Cada acción debe ser una tupla de longitud 2 [rueda, pasos]", 2, action.length);
                
                int wheel = action[0];
                int steps = action[1];
    
                // Validación del identificador de la rueda (1-based index)
                assertTrue("El identificador de la rueda (" + wheel + ") debe estar entre 1 y " + n, 
                           wheel >= 1 && wheel <= n);
    
                // Validación de los pasos de rotación
                assertTrue("Los pasos de giro deben ser estrictamente positivos", steps > 0);
                assertTrue("Los pasos de giro (" + steps + ") deben ser menores a una vuelta completa (" + n + ")", 
                           steps < n);
            }
    }
    /**
    * Verifica que tras ejecutar solve(n), la máquina resuelva efectivamente
    * el juego y quede en estado de Jackpot (exactamente 1 símbolo distinto).
    */
    @Test
    public void accordingDpLaShouldLeaveMachineInJackpotStateAfterSolve() {
        int n = 3;
        
        // Ejecutamos la solución
        int[][] actions = SlotMachineContest.solve(n);

        assertNotNull("La lista de acciones no debe ser null", actions);
        assertNotNull("La máquina interna de contest debe haberse inicializado", 
                    SlotMachineContest.machine);

        // Verificamos que la máquina haya alcanzado el Jackpot
        assertEquals("Al finalizar solve, la máquina debe tener exactamente 1 símbolo distinto", 
                    1, SlotMachineContest.machine.distinctSymbols());
        
        assertTrue("El método isJackpot() debe retornar true tras la resolución", 
                SlotMachineContest.machine.isJackpot());
    }

}

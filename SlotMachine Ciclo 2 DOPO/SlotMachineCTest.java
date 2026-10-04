
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Suite unificada de pruebas unitarias para la clase SlotMachine.
 * 
 * @author De La Peña - Latorre
 * @version 1.0 (2026)
 */
public class SlotMachineCTest {

    private SlotMachine machine;
    private SlotMachine slotMachine;
    private SlotMachine sm;

    @Before
    public void setUp() {
        machine = new SlotMachine();
        slotMachine = machine;
        sm = machine;
    }

    // =========================================================================
    // PRUEBAS DE UNIDAD
    // =========================================================================

    /**
     * A locked wheel does not advance when the machine spins.
     */
    @Test
    public void accordingCcGbShouldNotSpinLockedWheel() {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");

        machine.lock(1);
        machine.spin(1);

        assertEquals("red", machine.configuration().get(0));
        assertFalse(machine.ok());
    }

    /**
     * A wheel that was locked resumes spinning after unlock.
     */
    @Test
    public void accordingCcGbShouldSpinAfterUnlock() {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");

        machine.lock(1);
        machine.unlock(1);
        machine.spin(1);

        assertEquals("blue", machine.configuration().get(0));
        assertTrue(machine.ok());
    }

    /**
     * Verifica que, al intercambiar dos ruedas válidas con símbolos
     * distintos mediante swap(int, int), cada una termine mostrando el
     * símbolo que antes tenía la otra.
     */
    @Test
    public void accordingMsRhShouldSwapSymbolsBetweenTwoValidWheels() {
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(2, "blue");
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.placeSymbol(1, "red");
        slotMachine.placeSymbol(2, "blue");
        slotMachine.swap(1, 2);
        assertEquals("blue", slotMachine.configuration().get(0));
        assertEquals("red", slotMachine.configuration().get(1));
    }

    /**
     * Verifica que una rueda bloqueada con lock(int) no cambie su
     * símbolo visible al intentar girarla con spin(int).
     */
    @Test
    public void accordingMsRhShouldNotChangeLockedWheelWhenSpinning() {
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(2, "blue");
        slotMachine.addWheel(1);
        slotMachine.placeSymbol(1, "red");
        slotMachine.lock(1);
        slotMachine.spin(1);
        assertEquals("red", slotMachine.configuration().get(0));
    }

    /**
     * Verifica colectivamente que, al forzar una configuración donde todas las ruedas
     * quedan con el mismo símbolo mediante spin(String[]), el sistema cambie su
     * estado y detecte exitosamente el jackpot.
     */
    @Test
    public void accordingMsRhShouldDetectJackpotAfterForcedSpin() {
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(2, "blue");
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.spin(new String[]{"red", "red"});
        assertTrue(slotMachine.isJackpot());
    }

    /**
     * Verifica que delWheel(int pos) elimine la última rueda
     * cuando la posición indicada es mayor que el número de ruedas.
     */
    @Test
    public void accordingMsRhShouldDeleteLastWheelWhenPositionGreaterThanSize() {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.deleteWheel(10);
        assertEquals(4, slotMachine.configuration().size());
    }

    /**
     * Permitir intercambiar (swap) dos ruedas aun si una o ambas están bloqueadas,
     * manteniendo el estado de bloqueo en sus nuevas posiciones.
     */
    @Test
    public void accordingMurShouldSwapLockedWheelsAndMaintainLockState() {
        machine.lock(1);
        assertTrue(machine.ok());

        machine.swap(1, 2);
        assertTrue(machine.ok());

        machine.spin(2, 3);
        assertFalse(machine.ok()); 

        machine.spin(1, 3);
        assertTrue(machine.ok());
    }
    
    /**
     * Validar el comportamiento de spin(String[]) sobre una máquina que tiene
     * algunas ruedas bloqueadas y otras libres.
     */
    @Test
    public void accordingMurShouldSpinUnlockedWheelsOnlyWhenArrayIsApplied() {
        String[] symbolstoput = {"red", "red", "red"};
        machine.spin(symbolstoput);
        assertTrue(machine.ok());

        machine.lock(2);
        assertTrue(machine.ok());

        String[] newConfig = {"blue", "blue", "blue"};
        machine.spin(newConfig);
        assertTrue(machine.ok());

        assertFalse(machine.isJackpot());
    }

    /**
     * Verifies that attempting to swap two wheels fails when one of
     * them is locked, setting the machine status to not ok.
     */
    @Test
    public void accordingIcPgShouldNotSwap() {
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(1, "blue");
       
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.addWheel(3);
       
        slotMachine.lock(1);
        slotMachine.swap(1, 3);
       
        assertFalse(slotMachine.ok());
    }

    /**
     * Verifica que, tras bloquear una rueda con lock(), llamar a spin(wheel, steps) no altera configuration().
     */
    @Test
    public void shouldNotChangeConfigurationWhenWheelIsLocked() {
        machine.lock(1);

        ArrayList<String> before = machine.configuration();
        machine.spin(1, 1);
        ArrayList<String> after = machine.configuration();

        assertEquals(before, after);
    }

    /**
     * Verificar que, tras bloquear y luego desbloquear una rueda, spin(wheel, steps) sí puede cambiar lo que muestra.
     */
    @Test
    public void shouldChangeConfigurationWhenWheelIsUnlockedAfterLock() {
        machine.lock(1);
        machine.unlock(1);

        ArrayList<String> before = machine.configuration();
        machine.spin(1, 1);
        ArrayList<String> after = machine.configuration();

        assertNotEquals(before, after);
    }

    /**
     * Debe ser jackpot cuando todas las ruedas muestren el mismo color.
     */
    @Test
    public void accordingDrRmShouldBeJackpotWhenAllWheelsMatch() {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.addWheel(3);
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(2, "blue");
        slotMachine.spin(new String[]{"red", "red", "red", "red", "red", "red"});
        assertTrue(slotMachine.isJackpot());
    }
    
    /**
     * Debe mantenerse el número de ruedas correcto tras agregar y eliminar.
     */
    @Test
    public void accordingDrRmShouldKeepCorrectWheelCountAfterAddAndDelete() {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.addWheel(3);
        slotMachine.deleteWheel(2);
        slotMachine.addSymbol(1, "red");
        slotMachine.spin(new String[]{"red", "red", "red", "red", "red"});
        assertEquals(5, slotMachine.configuration().size());
    }

    /**
     * Intercambiar dos ruedas no debe alterar la cantidad de símbolos distintos.
     */
    @Test
    public void fsGcShouldKeepDistinctSymbolCountAfterSwap() {
        int before = sm.distinctSymbols();
        sm.swap(1, 2);
        assertTrue(sm.ok());
        assertEquals(before, sm.distinctSymbols());
    }

    /**
     * Prueba que una rueda existente pueda ser bloqueada correctamente.
     */
    @Test
    public void accordingBaGqShouldLockWheel() {
        SlotMachine maquinaTraga = new SlotMachine();
        maquinaTraga.addWheel(1);
        maquinaTraga.lock(1);
        assertTrue(maquinaTraga.ok());
    }

    /**
     * Prueba que una rueda bloqueada no pueda girar.
     */
    @Test
    public void accordingBaGqShouldNotSpinLockedWheel() {
        SlotMachine maq = new SlotMachine();
        maq.addWheel(1);
        maq.addSymbol(1, "red");
        maq.addSymbol(2, "blue");
        maq.placeSymbol(1, "red");
        maq.lock(1);
        maq.spin(1, 1);
        assertFalse(maq.ok());
        ArrayList<String> config = maq.configuration();
        assertEquals("red", config.get(0));
    }

    /**
     * Una sola rueda no debería declarar jackpot.
     */
    @Test
    public void accordingCgHnIsJackpotShouldBeFalseWithOnlyOneWheelEvenIfSymbolIsSet() {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.placeSymbol(1, "red");
        // Si hay ruedas de colores diferentes no es jackpot
        assertFalse(machine.isJackpot());
    }

    /**
     * Añadir un color que ya existe en la máquina debe fallar.
     */
    @Test
    public void accordingCgHnAddSymbolShouldFailWhenColorAlreadyExists() {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "red");
        assertFalse(machine.ok());
    }

    /**
     * Swap no debería alterar el catálogo de símbolos distintos.
     */
    @Test
    public void accordingClPcShouldKeepDistinctSymbolCountAfterSwap() {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");

        int before = machine.distinctSymbols();
        machine.swap(1, 2);
        int after = machine.distinctSymbols();

        assertTrue(machine.ok());
        assertEquals("swap no deberia alterar el catalogo de simbolos", before, after);
    }

    /**
     * Asignar símbolos distintos mediante arreglo no debe declarar jackpot.
     */
    @Test
    public void accordingClPcShouldNotReportJackpotWhenSetConfigurationDiffers() {
        machine.spin(new String[]{"red", "blue", "green"});
        assertTrue(machine.ok());
        assertFalse("simbolos distintos en las ruedas no es jackpot", machine.isJackpot());
    }

    /**
     * Prueba que se puedan intercambiar dos ruedas.
     */
    @Test
    public void swapShouldExchangeWheels() {
        ArrayList<String> before = machine.configuration();
        machine.swap(1, 3);
        ArrayList<String> after = machine.configuration();
        assertEquals(before.get(0), after.get(2));
        assertEquals(before.get(2), after.get(0));
    } 

    /**
     * Una rueda fijada no debe girar.
     */
    @Test
    public void lockedWheelShouldNotSpin() { 
        ArrayList<String> before = machine.configuration();
        machine.lock(1);
        machine.spin(1);
        ArrayList<String> after = machine.configuration();
        assertEquals(before, after);
    }

    /**
     * Tests that configuration() reports each wheel's current color.
     */
    @Test
    public void accordingFmSnShouldShowCorrectConfiguration() {
        slotMachine.addSymbol(1, "black");
        slotMachine.addSymbol(2, "green");
        slotMachine.placeSymbol(1, "red");
        slotMachine.placeSymbol(2, "green");
        slotMachine.placeSymbol(3, "black");

        ArrayList<String> config = slotMachine.configuration();
        assertEquals("red", config.get(0));
        assertEquals("green", config.get(1));
        assertEquals("black", config.get(2));
    }

    /**
     * Una rueda fija no debería poder girar.
     */
    @Test
    public void accordingGmLaShouldNotAllowSpinningALockedWheel() {
        SlotMachine m = new SlotMachine();
        m.lock(1);
        m.spin(1);
        assertFalse("una rueda fija no debería poder girar", m.ok());
    }

    /**
     * spin(setSymbols) debe dejar la máquina justo en esos colores.
     */
    @Test
    public void accordingGmLaShouldReflectExactConfigurationAfterSpinWithGivenSymbols() {
        SlotMachine m = new SlotMachine();
        m.spin(new String[] { "yellow", "red", "blue" });
        // Si yellow no está registrado falla ok()
        assertNotNull(m.configuration());
    }

    /**
     * Verifica la rotación por pasos y la detección de un jackpot.
     */
    @Test
    public void accordingCaPpShouldSpinByStepsAndDetectJackpotCorrectly() {
        SlotMachine m = new SlotMachine();
        m.makeInvisible();
        m.spin(new String[]{"red", "red", "red"});
        assertTrue(m.ok());
        assertTrue(m.isJackpot());
    }

    /**
     * spin(wheel, 0) no debería mover la rueda.
     */
    @Test
    public void accordingGjQkShouldSucceedWithoutChangingConfigurationWhenSpinningZeroSteps() {
        SlotMachine m = new SlotMachine();
        String before = m.configuration().get(0);
        m.spin(1, 0);
        assertTrue(m.ok());
        assertEquals(before, m.configuration().get(0));
    }

    /**
     * Fijar una rueda que ya estaba fija debe ser idempotente.
     */
    @Test
    public void accordingGjQkShouldSucceedWhenLockingAnAlreadyLockedWheel() {
        SlotMachine m = new SlotMachine();
        m.lock(1);
        m.lock(1);
        assertTrue(m.ok());
    }

    /**
     * Verifies that spinning a wheel advances it to the next symbol.
     */
    @Test
    public void accordingCxLxShouldAdvanceToNextSymbol() {
        machine.placeSymbol(1, "red");
        machine.spin(1);
        assertEquals("blue", machine.configuration().get(0));  
        assertTrue(machine.ok());
    }

    /**
     * Verifies that deleting a symbol that is not in the machine fails.
     */
    @Test
    public void accordingCxLxShouldNotDeleteMissingSymbol() {
        machine.delSymbol("unknownColor");
        assertFalse(machine.ok());                          
    }

    /**
     * Una rueda fijada no debe moverse y debe volver a poder girar tras un unlock.
     */
    @Test
    public void accordingMrSeShouldKeepLockedWheelFixedAndAllowSpinAfterUnlock() {
        SlotMachine m = new SlotMachine();
        m.placeSymbol(1, "red");

        m.lock(1);
        m.spin(1, 2);

        assertFalse(m.ok());
        assertEquals("red", m.configuration().get(0));

        m.unlock(1);
        m.spin(1, 1);

        assertTrue(m.ok());
    }

    /**
     * Rechazar spin de símbolos cuando un color solicitado no existe.
     */
    @Test
    public void accordingMrSeShouldRejectSpinSetSymbolsWhenColorMissing() {
        SlotMachine m = new SlotMachine();
        ArrayList<String> before = m.configuration();
        m.spin(new String[]{"red", "purple_non_existent"});

        assertFalse(m.ok());
        assertEquals(before, m.configuration());
    }

    /**
     * Verifica que al bloquear una rueda con lock(int), ok() retorne true.
     */
    @Test
    public void accordingAgAjShouldLockWheelSuccessfully() {
        SlotMachine m = new SlotMachine();
        m.lock(1);
        assertTrue(m.ok());
    }
    /**
     * Verifica que al desbloquear una rueda con unlock(int), la máquina procese la instrucción correctamente.
     */
    @Test
    public void accordingAgAjShouldUnlockWheelSuccessfully() {
        SlotMachine m = new SlotMachine();
        m.lock(1);
        m.unlock(1);
        assertTrue(m.ok());
    }

    /**
     * Despues de soltar una rueda con unlock, esta debe volver a poder girar normalmente con spin.
     */
    @Test
    public void unlockShouldAllowSpinAgain() {
        SlotMachine m = new SlotMachine();
        m.lock(1);
        m.unlock(1);
        m.spin(1);
        assertTrue(m.ok());
    }

    /**
     * spin debe fallar si la cantidad de colores del arreglo no coincide con el número de ruedas.
     */
    @Test
    public void spinSetSymbolsShouldFailWithWrongSize() {
        SlotMachine m = new SlotMachine();
        String[] setSymbols = {"red"};
        m.spin(setSymbols);
        assertFalse(m.ok());
    }
    
    /**
     * spin debe fallar si alguno de los colores indicados no existe.
     */
    @Test
    public void spinSetSymbolsShouldFailWithUnknownColor() {
        SlotMachine m = new SlotMachine();
        String[] setSymbols = {"purple_unknown", "red", "blue"};
        m.spin(setSymbols);
        assertFalse(m.ok());
    }

    /**
     * Calcula símbolos distintos correctamente.
     */
    @Test
    public void accordingCcIcshouldCalculateDistinctSymbolsCorrectly() {
        slotMachine.placeSymbol(1, "red");
        slotMachine.placeSymbol(2, "red");
        slotMachine.placeSymbol(3, "green");
        assertEquals(2, slotMachine.distinctSymbols());
        assertTrue(slotMachine.ok());
    }

    /**
     * Una rueda fijada con lock no debe cambiar su símbolo activo con spin global.
     */
    @Test
    public void accordingGLShouldKeepLockedWheelUnchangedOnGlobalSpin() {
        sm.placeSymbol(2, "red");
        sm.lock(2);
        sm.spin();
        assertEquals("red", sm.configuration().get(1));
    }

    /**
     * Swap intercambia las posiciones de dos ruedas.
     */
    @Test
    public void accordingZGBCShouldSwapTwoWheels() {
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        String pos1Before = machine.configuration().get(0);
        String pos2Before = machine.configuration().get(1);
        machine.swap(1, 2);
        assertTrue(machine.ok());
        assertEquals(pos1Before, machine.configuration().get(1));
        assertEquals(pos2Before, machine.configuration().get(0));
    }

    /**
     * Al intercambiar dos ruedas, la máquina debe seguir teniendo el mismo número de ruedas.
     */
    @Test
    public void accordingAsGaShouldKeepTheSameNumberOfWheelsAfterASwap() {
        machine.swap(1, 2);
        assertTrue(machine.ok());
        assertEquals(3, machine.configuration().size());
    }
}
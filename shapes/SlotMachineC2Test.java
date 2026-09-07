import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Pruebas de unidad de SlotMachine para el ciclo 2. Todas las pruebas
 * corren en modo invisible (nunca se llama makeVisible()), tal como
 * lo pide el requisito de entrega: asi ningun JOptionPane bloquea la
 * ejecucion y las pruebas terminan solas.
 *
 * Cada prueba se diseno pensando en una de dos preguntas:
 *   - Que SI deberia hacer la maquina (casos validos).
 *   - Que NO deberia hacer la maquina (casos invalidos, que deben
 *     dejar ok() en false y no cambiar el estado de forma incorrecta).
 *
 * @author Proyecto DOPO-POOB
 * @version 1.0
 */
public class SlotMachineC2Test
{
    private SlotMachine machine;

    /**
     * Antes de cada prueba se crea una maquina nueva con 3 ruedas y
     * los colores red, blue, green en cada una (en ese orden). Como
     * ninguna rueda ha girado todavia, las tres quedan mostrando
     * "red" (la posicion 0 por defecto).
     */
    @Before
    public void setUp()
    {
        machine = new SlotMachine();
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
    }

    // ================= QUE SI DEBERIA HACER =================

    @Test
    public void newMachineShouldStartEmptyAndOk()
    {
        SlotMachine empty = new SlotMachine();
        assertTrue(empty.ok());
        assertEquals(0, empty.configuration().length);
    }

    @Test
    public void addWheelShouldNormalizeLowPosition()
    {
        SlotMachine empty = new SlotMachine();
        empty.addWheel(0); // pos < 1 -> se usa 1
        assertTrue(empty.ok());
        assertEquals(1, empty.configuration().length);
    }

    @Test
    public void addWheelShouldNormalizeHighPosition()
    {
        SlotMachine empty = new SlotMachine();
        empty.addWheel(1);
        empty.addWheel(99); // pos > max -> se agrega al final
        assertTrue(empty.ok());
        assertEquals(2, empty.configuration().length);
    }

    @Test
    public void addSymbolShouldApplyToAllWheels()
    {
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"red", "blue", "green"}, machine.symbols());
    }

    @Test
    public void delSymbolShouldRemoveFromAllWheels()
    {
        machine.delSymbol("blue");
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"red", "green"}, machine.symbols());
    }

    @Test
    public void placeSymbolShouldChangeVisibleSymbol()
    {
        machine.placeSymbol(2, "green");
        assertTrue(machine.ok());
        assertEquals("green", machine.configuration()[1]);
    }

    @Test
    public void spinOneWheelShouldKeepResultAmongItsSymbols()
    {
        machine.spin(1);
        assertTrue(machine.ok());
        String result = machine.configuration()[0];
        assertTrue(result.equals("red") || result.equals("blue") || result.equals("green"));
    }

    @Test
    public void configurationLengthShouldMatchWheelCount()
    {
        assertEquals(3, machine.configuration().length);
    }

    @Test
    public void distinctSymbolsShouldCountUniqueVisibleColors()
    {
        // por defecto las 3 ruedas muestran "red"
        assertEquals(1, machine.distinctSymbols());

        machine.placeSymbol(2, "blue");
        assertEquals(2, machine.distinctSymbols());
    }

    @Test
    public void isJackpotShouldBeTrueWhenAllWheelsMatch()
    {
        // configuracion por defecto: las 3 ruedas en "red"
        assertTrue(machine.isJackpot());
    }

    @Test
    public void isJackpotShouldBeFalseWhenWheelsDiffer()
    {
        machine.placeSymbol(2, "blue");
        assertFalse(machine.isJackpot());
    }

    @Test
    public void swapShouldExchangeVisibleSymbolsBetweenWheels()
    {
        machine.placeSymbol(2, "blue");
        machine.swap(1, 2);
        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[0]);
        assertEquals("red", machine.configuration()[1]);
    }

    @Test
    public void unlockShouldAllowSpinningAgain()
    {
        machine.lock(1);
        machine.unlock(1);
        machine.spin(1);
        assertTrue(machine.ok());
    }

    @Test
    public void spinWithStepsShouldMoveForwardInOrder()
    {
        machine.placeSymbol(1, "red");
        machine.spin(1, 1); // orden de la rueda: red, blue, green
        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[0]);
    }

    @Test
    public void spinWithNegativeStepsShouldMoveBackward()
    {
        machine.placeSymbol(1, "red");
        machine.spin(1, -1); // deberia dar la vuelta hasta el ultimo simbolo
        assertTrue(machine.ok());
        assertEquals("green", machine.configuration()[0]);
    }

    @Test
    public void spinWithStepsShouldWrapAroundAtTheEnd()
    {
        machine.placeSymbol(1, "green"); // ultima posicion de la rueda
        machine.spin(1, 1);
        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[0]); // vuelve al inicio
    }

    @Test
    public void spinWithConfigurationShouldApplyEveryColor()
    {
        machine.spin(new String[]{"green", "red", "blue"});
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"green", "red", "blue"}, machine.configuration());
    }

    @Test
    public void spinWithConfigurationShouldSkipLockedWheels()
    {
        machine.placeSymbol(1, "red");
        machine.lock(1);
        machine.spin(new String[]{"blue", "blue", "blue"});
        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[0]);  // no cambio, seguia fija
        assertEquals("blue", machine.configuration()[1]);
        assertEquals("blue", machine.configuration()[2]);
    }

    @Test
    public void exitShouldNotThrowEvenWithoutBeingVisible()
    {
        machine.exit();
        assertTrue(true); // si llega hasta aqui, no lanzo ninguna excepcion
    }

    // ================= QUE NO DEBERIA HACER =================

    @Test
    public void delWheelOnEmptyMachineShouldFail()
    {
        SlotMachine empty = new SlotMachine();
        empty.delWheel(1);
        assertFalse(empty.ok());
    }

    @Test
    public void addSymbolWithoutWheelsShouldFail()
    {
        SlotMachine empty = new SlotMachine();
        empty.addSymbol(1, "red");
        assertFalse(empty.ok());
    }

    @Test
    public void delSymbolNotFoundShouldFail()
    {
        machine.delSymbol("purple");
        assertFalse(machine.ok());
    }

    @Test
    public void placeSymbolOnInvalidWheelShouldFail()
    {
        machine.placeSymbol(10, "red");
        assertFalse(machine.ok());
    }

    @Test
    public void placeSymbolWithUnknownColorShouldFail()
    {
        machine.placeSymbol(1, "purple");
        assertFalse(machine.ok());
    }

    @Test
    public void spinInvalidWheelShouldFail()
    {
        machine.spin(10);
        assertFalse(machine.ok());
    }

    @Test
    public void spinAllOnEmptyMachineShouldFail()
    {
        SlotMachine empty = new SlotMachine();
        empty.spin();
        assertFalse(empty.ok());
    }

    @Test
    public void symbolsOnEmptyMachineShouldFail()
    {
        SlotMachine empty = new SlotMachine();
        String[] result = empty.symbols();
        assertFalse(empty.ok());
        assertEquals(0, result.length);
    }

    @Test
    public void swapWithInvalidWheelShouldFail()
    {
        machine.swap(1, 10);
        assertFalse(machine.ok());
    }

    @Test
    public void swapShouldFailIfEitherWheelIsLocked()
    {
        machine.lock(1);
        machine.swap(1, 2);
        assertFalse(machine.ok());
    }

    @Test
    public void lockedWheelShouldNotSpin()
    {
        machine.lock(1);
        machine.spin(1);
        assertFalse(machine.ok());
    }

    @Test
    public void spinWithStepsOnLockedWheelShouldFail()
    {
        machine.lock(1);
        machine.spin(1, 2);
        assertFalse(machine.ok());
    }

    @Test
    public void spinWithStepsOnInvalidWheelShouldFail()
    {
        machine.spin(10, 1);
        assertFalse(machine.ok());
    }

    @Test
    public void spinWithConfigurationOfWrongLengthShouldFail()
    {
        machine.spin(new String[]{"red", "blue"}); // solo 2, pero hay 3 ruedas
        assertFalse(machine.ok());
    }

    @Test
    public void spinWithConfigurationOfUnknownColorShouldFail()
    {
        machine.spin(new String[]{"red", "blue", "purple"});
        assertFalse(machine.ok());
    }
}

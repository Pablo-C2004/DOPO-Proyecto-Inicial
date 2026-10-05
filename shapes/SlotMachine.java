import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

/**
 * Clase principal del simulador. Representa una maquina tragamonedas
 * con varias ruedas. Permite crear/eliminar ruedas y simbolos, girar
 * las ruedas, consultar el estado del juego y mostrar/ocultar la
 * maquina en pantalla.
 */
public class SlotMachine
{
    private static final int STEP_DELAY_MS = 200;

    private ArrayList<Wheel> wheels;
    private MachineDisplay display;
    private boolean visible;
    private boolean lastOperationOk;

    /**
     * Crea una maquina tragamonedas vacia (sin ruedas ni simbolos) y
     * la deja oculta.
     */
    public SlotMachine()
    {
        wheels = new ArrayList<Wheel>();
        display = new MachineDisplay();
        visible = false;
        lastOperationOk = true;
    }

    /**
     * Crea una maquina con n ruedas y n simbolos (un color distinto
     * por simbolo), inicial al azar que nunca es jackpot de entrada.
     * 
     * @param n numero de ruedas (y de simbolos distintos por rueda)
     */
    public SlotMachine(int n)
    {
        this();
        if (n < 1)
        {
            lastOperationOk = false;
            return;
        }

        String[] colors = generateDistinctColors(n);
        for (int i = 1; i <= n; i++)
        {
            addWheel(i);
        }
        for (int i = 1; i <= n; i++)
        {
            addSymbol(i, colors[i - 1]);
        }

        randomizeWithoutJackpot();
        lastOperationOk = true;
    }

    /**
     * Agrega una rueda normal en la posicion indicada.
     *
     * @param pos posicion donde se inserta la rueda (empieza en 1)
     */
    public void addWheel(int pos)
    {
        addWheel("normal", pos);
    }

    /**
     * Agrega una rueda del tipo indicado en la posicion dada. Los
     * tipos son "normal", "lefty", "rebel" y "wild"                                                                    
     *
     * @param type tipo de rueda a crear
     * @param pos posicion donde se inserta la rueda (empieza en 1)
     */
    public void addWheel(String type, int pos)
    {
        int index = normalize(pos, wheels.size() + 1);
        wheels.add(index - 1, createWheel(type));
        rewireLeftyNeighbors();
        lastOperationOk = true;
        refreshDisplay();
    }

    /**
     * Elimina la rueda que se encuentra en la posicion indicada. Una
     * rueda de tipo rebel no se deja eliminar.
     *
     * @param pos posicion de la rueda a eliminar (empieza en 1)
     */
    public void delWheel(int pos)
    {
        if (wheels.isEmpty())
        {
            reportError("No hay ruedas para eliminar");
            return;
        }
        int index = normalize(pos, wheels.size());
        if (!wheels.get(index - 1).allowsRemoval())
        {
            reportError("Esa rueda no se deja eliminar");
            return;
        }
        wheels.remove(index - 1);
        rewireLeftyNeighbors();
        lastOperationOk = true;
        refreshDisplay();
    }

    /**
     * Agrega un simbolo normal identificado por su color en la
     * posicion indicada, en todas las ruedas. Es un atajo para
     * addSymbol("normal", pos, color).
     *
     * @param pos posicion del simbolo dentro de cada rueda (empieza en 1)
     * @param color color del nuevo simbolo, en formato CSS
     */
    public void addSymbol(int pos, String color)
    {
        addSymbol("normal", pos, color);
    }

    /**
     * Agrega un simbolo del tipo indicado identificado por su color
     * en la posicion dada, en todas las ruedas de la maquina. Los
     * tipos son "normal", "ephemeral", "shy" y "growing"
     *
     * @param type tipo de simbolo a crear
     * @param pos posicion del simbolo dentro de cada rueda (empieza en 1)
     * @param color color del nuevo simbolo, en formato CSS
     */
    public void addSymbol(String type, int pos, String color)
    {
        if (wheels.isEmpty())
        {
            reportError("Debe existir al menos una rueda antes de agregar simbolos");
            return;
        }
        for (Wheel wheel : wheels)
        {
            wheel.addSymbol(pos, createSymbol(type, color));
        }
        lastOperationOk = true;
        refreshDisplay();
    }

    /**
     * Elimina el simbolo del color indicado en todas las ruedas donde
     * exista.
     *
     * @param symbol color del simbolo a eliminar
     */
    public void delSymbol(String symbol)
    {
        boolean found = false;
        for (Wheel wheel : wheels)
        {
            if (wheel.delSymbol(symbol))
            {
                found = true;
            }
        }
        if (!found)
        {
            reportError("El simbolo " + symbol + " no existe");
            return;
        }
        lastOperationOk = true;
        refreshDisplay();
    }

    /**
     * Hace que la rueda indicada muestre el simbolo del color dado.
     *
     * @param wheel numero de la rueda (empieza en 1)
     * @param symbol color del simbolo que se quiere mostrar
     */
    public void placeSymbol(int wheel, String symbol)
    {
        if (!wheelExists(wheel))
        {
            reportError("La rueda " + wheel + " no existe");
            return;
        }
        if (!wheels.get(wheel - 1).placeSymbol(symbol))
        {
            reportError("El simbolo " + symbol + " no esta en esa rueda");
            return;
        }
        lastOperationOk = true;
        refreshDisplay();
    }

    /**
     * Gira una sola rueda. Una rueda fija no gira; las demas giran
     * cada una a su manera.
     *
     * @param wheel numero de la rueda a girar (empieza en 1)
     */
    public void spin(int wheel)
    {
        if (!wheelExists(wheel))
        {
            reportError("La rueda " + wheel + " no existe");
            return;
        }
        if (wheels.get(wheel - 1).isLocked())
        {
            reportError("La rueda " + wheel + " esta fija");
            return;
        }
        wheels.get(wheel - 1).spin();
        lastOperationOk = true;
        refreshDisplay();
    }

    /**
     * Gira todas las ruedas de la maquina. Las ruedas fijas se dejan
     * tal como estan.
     */
    public void spin()
    {
        if (wheels.isEmpty())
        {
            reportError("No hay ruedas para girar");
            return;
        }
        boolean spunAtLeastOne = false;
        for (Wheel wheel : wheels)
        {
            if (!wheel.isLocked())
            {
                wheel.spin();
                spunAtLeastOne = true;
            }
        }
        if (!spunAtLeastOne)
        {
            reportError("Todas las ruedas estan fijas");
            return;
        }
        lastOperationOk = true;
        refreshDisplay();
    }

    /**
     * Intercambia el contenido completo de dos ruedas
     * Ninguna de las dos puede estar fija, ni ser de tipo rebel.
     *
     * @param wheel1 numero de la primera rueda (empieza en 1)
     * @param wheel2 numero de la segunda rueda (empieza en 1)
     */
    public void swap(int wheel1, int wheel2)
    {
        if (!wheelExists(wheel1) || !wheelExists(wheel2))
        {
            reportError("Alguna de las ruedas indicadas no existe");
            return;
        }
        Wheel w1 = wheels.get(wheel1 - 1);
        Wheel w2 = wheels.get(wheel2 - 1);
        if (w1.isLocked() || w2.isLocked() || !w1.allowsSwap() || !w2.allowsSwap())
        {
            reportError("No se puede intercambiar una rueda fija o de tipo rebel");
            return;
        }
        Collections.swap(wheels, wheel1 - 1, wheel2 - 1);
        rewireLeftyNeighbors();
        lastOperationOk = true;
        refreshDisplay();
    }

    /**
     * Fija una rueda mientras este fija no gira ni se puede
     * intercambiar con otra. (Una rueda de tipo rebel ignora esto).
     *
     * @param wheel numero de la rueda a fijar (empieza en 1)
     */
    public void lock(int wheel)
    {
        if (!wheelExists(wheel))
        {
            reportError("La rueda " + wheel + " no existe");
            return;
        }
        wheels.get(wheel - 1).lock();
        lastOperationOk = true;
        refreshDisplay();
    }

    /**
     * Suelta una rueda previamente fijada.
     *
     * @param wheel numero de la rueda a soltar (empieza en 1)
     */
    public void unlock(int wheel)
    {
        if (!wheelExists(wheel))
        {
            reportError("La rueda " + wheel + " no existe");
            return;
        }
        wheels.get(wheel - 1).unlock();
        lastOperationOk = true;
        refreshDisplay();
    }

    /**
     * Rota una rueda un numero exacto de pasos. Un numero positivo
     * gira en un sentido, uno negativo en el sentido contrario. Si el
     * simulador esta visible, el giro se muestra paso a paso. Cada
     * tipo de rueda puede reaccionar distinto a esto (una wild avanza
     * el doble, una lefty copia a su vecina en vez de moverse sola).
     *
     * @param wheel numero de la rueda a rotar (empieza en 1)
     * @param steps cantidad de pasos a rotar (puede ser negativo)
     */
    public void spin(int wheel, int steps)
    {
        if (!wheelExists(wheel))
        {
            reportError("La rueda " + wheel + " no existe");
            return;
        }
        Wheel w = wheels.get(wheel - 1);
        if (w.isLocked())
        {
            reportError("La rueda " + wheel + " esta fija");
            return;
        }

        int direction = steps < 0 ? -1 : 1;
        int totalSteps = Math.abs(steps);
        for (int i = 0; i < totalSteps; i++)
        {
            w.rotateOneStep(direction);
            refreshDisplay();
            pauseIfVisible();
        }
        lastOperationOk = true;
    }

    /**
     * Deja la maquina mostrando exactamente la configuracion indicada,
     * una posicion por rueda. Las ruedas fijas no se modifican.
     *
     * @param setSymbols colores deseados por rueda, izquierda a derecha
     */
    public void spin(String[] setSymbols)
    {
        if (setSymbols.length != wheels.size())
        {
            reportError("La configuracion no tiene el mismo numero de ruedas");
            return;
        }
        for (int i = 0; i < wheels.size(); i++)
        {
            Wheel w = wheels.get(i);
            if (!w.isLocked() && !w.hasSymbol(setSymbols[i]))
            {
                reportError("El simbolo " + setSymbols[i] + " no existe en la rueda " + (i + 1));
                return;
            }
        }
        for (int i = 0; i < wheels.size(); i++)
        {
            Wheel w = wheels.get(i);
            if (!w.isLocked())
            {
                w.placeSymbol(setSymbols[i]);
            }
        }
        lastOperationOk = true;
        refreshDisplay();
    }

    /**
     * Retorna los colores de los simbolos en el orden en que estan
     * ubicados dentro de la rueda, iniciando por el 1.
     *
     * @return arreglo con los colores de los simbolos
     */
    public String[] symbols()
    {
        if (wheels.isEmpty())
        {
            lastOperationOk = false;
            return new String[0];
        }
        lastOperationOk = true;
        return wheels.get(0).symbolsList();
    }

    /**
     * Retorna la cantidad de simbolos distintos que se ven actualmente
     * en la ventana de la maquina.
     *
     * @return numero de simbolos distintos visibles
     */
    public int distinctSymbols()
    {
        String[] config = configuration();
        ArrayList<String> distinct = new ArrayList<String>();
        for (String color : config)
        {
            if (!distinct.contains(color))
            {
                distinct.add(color);
            }
        }
        lastOperationOk = true;
        return distinct.size();
    }

    /**
     * Retorna los colores de los simbolos visibles en todas las
     * ruedas de la maquina, ordenados de izquierda a derecha.
     *
     * @return arreglo con la configuracion actual
     */
    public String[] configuration()
    {
        String[] config = new String[wheels.size()];
        for (int i = 0; i < wheels.size(); i++)
        {
            config[i] = wheels.get(i).currentSymbol();
        }
        lastOperationOk = true;
        return config;
    }

    /**
     * Indica si la configuracion actual es ganadora si
     * todas las ruedas muestran el mismo simbolo.
     *
     * @return true si hay jackpot, false en caso contrario
     */
    public boolean isJackpot()
    {
        lastOperationOk = true;
        if (wheels.isEmpty())
        {
            return false;
        }
        return distinctSymbols() == 1;
    }

    /**
     * Hace visible el simulador.
     */
    public void makeVisible()
    {
        visible = true;
        display.makeVisible();
        refreshDisplay();
        lastOperationOk = true;
    }

    /**
     * Hace invisible el simulador.
     */
    public void makeInvisible()
    {
        visible = false;
        display.makeInvisible();
        lastOperationOk = true;
    }

    /**
     * Termina el simulador y cierra la ventana grafica.
     */
    public void exit()
    {
        display.makeInvisible();
        lastOperationOk = true;
    }

    /**
     * Indica si la ultima operacion realizada sobre la maquina se
     * pudo completar con exito.
     *
     * @return true si la ultima operacion fue exitosa
     */
    public boolean ok()
    {
        return lastOperationOk;
    }

    /**
     * Indica si existe una rueda en la posicion dada.
     */
    private boolean wheelExists(int pos)
    {
        return pos >= 1 && pos <= wheels.size();
    }

    /**
     * Ajusta una posicion pedida para que quede dentro del rango
     * valido (1, max).
     */
    private int normalize(int pos, int max)
    {
        if (pos < 1)
        {
            return 1;
        }
        if (pos > max)
        {
            return max;
        }
        return pos;
    }

    /**
     * Marca la ultima operacion como fallida y, solo si el simulador
     * esta visible, le avisa al usuario con un mensaje emergente.
     */
    private void reportError(String message)
    {
        lastOperationOk = false;
        if (visible)
        {
            JOptionPane.showMessageDialog(null, message);
        }
    }

    /**
     * Si el simulador esta visible, actualiza el dibujo con el estado
     * actual de la maquina.
     */
    private void refreshDisplay()
    {
        if (visible)
        {
            display.show(wheels, isJackpot());
        }
    }

    /**
     * Crea un objeto Wheel del tipo pedido.
     *
     * @param type nombre del tipo de rueda
     * @return una rueda nueva del tipo pedido (normal si no se reconoce)
     */
    private Wheel createWheel(String type)
    {
        if (type.equalsIgnoreCase("lefty"))
        {
            return new LeftyWheel();
        }
        if (type.equalsIgnoreCase("rebel"))
        {
            return new RebelWheel();
        }
        if (type.equalsIgnoreCase("wild"))
        {
            return new WildWheel();
        }
        return new NormalWheel();
    }

    /**
     * Crea un objeto Symbol del tipo pedido, con el color indicado.
     *
     * @param type nombre del tipo de simbolo
     * @param color color del simbolo
     * @return un simbolo nuevo del tipo pedido (normal si no se reconoce)
     */
    private Symbol createSymbol(String type, String color)
    {
        if (type.equalsIgnoreCase("ephemeral"))
        {
            return new EphemeralSymbol(color);
        }
        if (type.equalsIgnoreCase("shy"))
        {
            return new ShySymbol(color);
        }
        if (type.equalsIgnoreCase("growing"))
        {
            return new GrowingSymbol(color);
        }
        return new NormalSymbol(color);
    }

    /**
     * Le avisa a cada rueda quien es, en este momento, la rueda que
     * tiene a su izquierda. Se llama cada vez que el orden de las
     * ruedas puede haber cambiado (agregar, eliminar o intercambiar).
     */
    private void rewireLeftyNeighbors()
    {
        for (int i = 0; i < wheels.size(); i++)
        {
            Wheel left = i == 0 ? null : wheels.get(i - 1);
            wheels.get(i).updateLeftNeighbor(left);
        }
    }

    /**
     * Genera n colores distintos
     *
     * @param n cantidad de colores distintos a generar
     * @return arreglo con n colores en formato "#RRGGBB"
     */
    private String[] generateDistinctColors(int n)
    {
        String[] colors = new String[n];
        for (int i = 0; i < n; i++)
        {
            float hue = (float) i / n;
            java.awt.Color c = java.awt.Color.getHSBColor(hue, 0.85f, 0.85f);
            colors[i] = String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue());
        }
        return colors;
    }

    /**
     * Gira todas las ruedas al azar, repitiendo si por casualidad el
     * resultado ya fuera jackpot, para que la maquina nunca empiece
     * ya ganada (igual que exige el problema original de la maraton).
     */
    private void randomizeWithoutJackpot()
    {
        do
        {
            for (Wheel wheel : wheels)
            {
                wheel.spin();
            }
        }
        while (wheels.size() > 1 && isJackpot());
    }

    /**
     * Se usa para que un giro paso a paso se vea como una animacion,
     */
    private void pauseIfVisible()
    {
        if (visible)
        {
            try
            {
                Thread.sleep(STEP_DELAY_MS);
            }
            catch (InterruptedException e)
            {
                // se ignora: no afecta el resultado de la rotacion
            }
        }
    }
}

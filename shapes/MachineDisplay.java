import java.util.ArrayList;

/**
 * Representacion visual de la maquina tragamonedas, construida
 * reutilizando las clases del paquete shapes:
 */
public class MachineDisplay
{
    private static final int SPACING = 45;

    private ArrayList<Circle> circles;
    private ArrayList<Rectangle> wheelFrames;
    private Rectangle background;
    private boolean visible;

    /**
     * Crea la representacion visual.
     */
    public MachineDisplay()
    {
        circles = new ArrayList<Circle>();
        wheelFrames = new ArrayList<Rectangle>();
        background = new Rectangle();
        background.moveHorizontal(-50); // ubica el fondo en x = 20
        background.moveVertical(20);    // ubica el fondo en y = 35
        background.changeColor("gray");
        visible = false;
    }

    /**
     * Muestra la maquina en pantalla.
     */
    public void makeVisible()
    {
        visible = true;
        background.makeVisible();
        for (Circle circle : circles)
        {
            circle.makeVisible();
        }
        for (Rectangle frame : wheelFrames)
        {
            frame.makeVisible();
        }
    }

    /**
     * Oculta la maquina de la pantalla.
     */
    public void makeInvisible()
    {
        visible = false;
        background.makeInvisible();
        for (Circle circle : circles)
        {
            circle.makeInvisible();
        }
        for (Rectangle frame : wheelFrames)
        {
            frame.makeInvisible();
        }
    }

    /**
     * Redibuja la maquina completa: una ruedita (Rectangle de color
     * segun el tipo) por cada rueda, con su simbolo visible encima
     * (un Circle, con el tamano y color que le toque en este
     * momento, o ningun circulo si el simbolo esta escondido). El
     * fondo general cambia de color si la configuracion es ganadora.
     *
     * @param wheels las ruedas de la maquina, en orden
     * @param jackpot true si la combinacion actual es ganadora
     */
    public void show(ArrayList<Wheel> wheels, boolean jackpot)
    {
        if (!visible)
        {
            return;
        }

        clearPreviousDrawing();

        background.changeSize(110, Math.max(60, wheels.size() * SPACING + 20));
        background.changeColor(jackpot ? "gold" : "gray");

        for (int i = 0; i < wheels.size(); i++)
        {
            drawWheel(wheels.get(i), i);
        }
    }

    /**
     * Quita del lienzo todo lo que se habia dibujado en la actualizacion
     * anterior, para empezar el dibujo de nuevo desde cero.
     */
    private void clearPreviousDrawing()
    {
        for (Circle circle : circles)
        {
            circle.makeInvisible();
        }
        circles.clear();
        for (Rectangle frame : wheelFrames)
        {
            frame.makeInvisible();
        }
        wheelFrames.clear();
    }

    /**
     * Dibuja una sola rueda color (segun el tipo) y
     * el simbolo que tiene visible en este momento.
     *
     * @param wheel la rueda a dibujar
     * @param position en que posicion de la fila va (0 es la primera)
     */
    private void drawWheel(Wheel wheel, int position)
    {
        Rectangle frame = new Rectangle();
        frame.moveHorizontal(position * SPACING - 50);
        frame.moveVertical(40);
        frame.changeSize(60, 40);
        frame.changeColor(frameColorFor(wheel));
        frame.makeVisible();
        wheelFrames.add(frame);

        Symbol symbol = wheel.getCurrentSymbol();
        if (symbol != null && symbol.isVisible())
        {
            Circle circle = new Circle();
            circle.moveHorizontal(position * SPACING);
            circle.moveVertical(45);
            circle.changeSize(symbol.getSize());
            circle.changeColor(symbol.getColor());
            circle.makeVisible();
            circles.add(circle);
        }
    }

    /**
     * Da un color de marco distinto segun el tipo de rueda.
     *
     * @param wheel la rueda a la que hay que ponerle marco
     * @return el nombre de color que le corresponde a su tipo
     */
    private String frameColorFor(Wheel wheel)
    {
        String label = wheel.getTypeLabel();
        if (label.equals("L"))
        {
            return "navy";
        }
        if (label.equals("R"))
        {
            return "maroon";
        }
        if (label.equals("W"))
        {
            return "purple";
        }
        return "lightgray";
    }
}

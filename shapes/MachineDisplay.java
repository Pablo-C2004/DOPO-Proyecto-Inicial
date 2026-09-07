import java.util.ArrayList;

/**
 * Representacion visual de la maquina tragamonedas, construida
 * REUTILIZANDO las clases del paquete shapes:
 *   - un Rectangle representa el "cuerpo" de la maquina y cambia de
 *     color cuando la combinacion actual es ganadora.
 *   - un Circle por cada rueda representa el simbolo que esa rueda
 */
public class MachineDisplay
{
    private static final int SPACING = 40; // separacion horizontal entre circulos

    private ArrayList<Circle> circles;
    private Rectangle background;
    private boolean visible;

    /**
     * Crea la representacion visual (todavia oculta).
     */
    public MachineDisplay()
    {
        circles = new ArrayList<Circle>();
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
    }

    /**
     * Redibuja la maquina completa con la configuracion actual: borra
     * los circulos anteriores y crea uno nuevo por cada simbolo
     * visible, en orden de izquierda a derecha. El fondo cambia de
     * color si la configuracion es ganadora.
     *
     * @param configuration colores actuales de cada rueda, izquierda a derecha
     * @param jackpot true si la combinacion actual es ganadora
     */
    public void show(String[] configuration, boolean jackpot)
    {
        if (!visible)
        {
            return;
        }

        for (Circle circle : circles)
        {
            circle.makeInvisible();
        }
        circles.clear();

        background.changeSize(80, Math.max(60, configuration.length * SPACING + 20));
        background.changeColor(jackpot ? "gold" : "gray");

        for (int i = 0; i < configuration.length; i++)
        {
            Circle circle = new Circle();
            circle.moveHorizontal(i * SPACING);
            circle.moveVertical(30);
            circle.changeColor(configuration[i]);
            circle.makeVisible();
            circles.add(circle);
        }
    }
}

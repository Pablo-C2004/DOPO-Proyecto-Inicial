/**
 * Representa, en general, un simbolo de la maquina tragamonedas.
 */
public abstract class Symbol implements Labeled
{
    protected static final int DEFAULT_SIZE = 30;

    protected String color;
    protected int size;
    protected boolean visible;

    /**
     * Crea un simbolo del color indicado, con tamaño normal y
     * visible desde el principio.
     *
     * @param color color del simbolo, en formato CSS
     */
    public Symbol(String color)
    {
        this.color = color;
        this.size = DEFAULT_SIZE;
        this.visible = true;
    }

    /**
     * Retorna el color de este simbolo.
     *
     * @return el color del simbolo
     */
    public String getColor()
    {
        return color;
    }

    /**
     * Retorna el tamaño con el que se deberia dibujar este simbolo
     * ahora.
     *
     * @return el tamaño actual, en pixeles
     */
    public int getSize()
    {
        return size;
    }

    /**
     * Dice si este simbolo se deberia ver en pantalla en este
     * momento, o si por ahora esta "escondido".
     *
     * @return true si el simbolo deberia dibujarse
     */
    public boolean isVisible()
    {
        return visible;
    }

    /**
     * Se llama cada vez que este simbolo pasa a ser el visible en su
     * rueda.
     */
    public void onSelected()
    {
        // un simbolo normal no reacciona a que lo seleccionen
    }
}

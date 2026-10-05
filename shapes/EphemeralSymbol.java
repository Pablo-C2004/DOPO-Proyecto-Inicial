/**
 * Un simbolo que se va "cansando": cada vez que le toca ser el
 * visible, se encoge un poco, hasta quedar reducido casi a un
 * puntico y no desaparecer nunca del todo.
 */
public class EphemeralSymbol extends Symbol
{
    private static final int MIN_SIZE = 6;
    private static final int SHRINK_STEP = 4;

    /**
     * Crea un simbolo efimero del color indicado, con tamaño normal.
     *
     * @param color color del simbolo
     */
    public EphemeralSymbol(String color)
    {
        super(color);
    }

    /**
     * Cada vez que este simbolo queda seleccionado se hace un poco
     * mas pequeño, sin pasar de un tamaño minimo.
     */
    @Override
    public void onSelected()
    {
        size = size - SHRINK_STEP;
        if (size < MIN_SIZE)
        {
            size = MIN_SIZE;
        }
    }

    /**
     * Retorna la etiqueta de este tipo de simbolo.
     *
     * @return siempre "E"
     */
    public String getTypeLabel()
    {
        return "E";
    }
}

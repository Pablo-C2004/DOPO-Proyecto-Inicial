/**
 * El tipo de simbolo nuevo para este ciclo: es lo
 * contrario del efimero. En vez de encogerse, cada vez que lo
 * seleccionan se va poniendo mas grande, hasta un tamaño maximo.
 */
public class GrowingSymbol extends Symbol
{
    private static final int MAX_SIZE = 60;
    private static final int GROW_STEP = 4;

    /**
     * Crea un simbolo creciente del color indicado, con tamaño normal.
     *
     * @param color color del simbolo
     */
    public GrowingSymbol(String color)
    {
        super(color);
    }

    /**
     * Cada vez que lo seleccionan crece un poco, sin pasar de un
     * tamaño maximo.
     */
    @Override
    public void onSelected()
    {
        size = size + GROW_STEP;
        if (size > MAX_SIZE)
        {
            size = MAX_SIZE;
        }
    }

    /**
     * Retorna la etiqueta de este tipo de simbolo.
     *
     * @return siempre "G"
     */
    public String getTypeLabel()
    {
        return "G";
    }
}

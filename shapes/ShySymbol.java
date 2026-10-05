/**
 * Un simbolo timido: cada vez que le toca ser el visible, cambia de
 * opinion y alterna entre mostrarse y esconderse.
 */
public class ShySymbol extends Symbol
{
    /**
     * Crea un simbolo timido del color indicado. Empieza visible.
     *
     * @param color color del simbolo
     */
    public ShySymbol(String color)
    {
        super(color);
    }

    /**
     * Cada vez que lo seleccionan, cambia su estado: si estaba
     * visible pasa a invisible, y si estaba invisible pasa a visible.
     */
    @Override
    public void onSelected()
    {
        visible = !visible;
    }

    /**
     * Retorna la etiqueta de este tipo de simbolo.
     *
     * @return siempre "S"
     */
    public String getTypeLabel()
    {
        return "S";
    }
}

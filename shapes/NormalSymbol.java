/**
 * El simbolo de toda la vida: solo tiene un color, y no le pasa nada
 * especial cuando lo seleccionan.
 */
public class NormalSymbol extends Symbol
{
    /**
     * Crea un simbolo normal del color indicado.
     *
     * @param color color del simbolo
     */
    public NormalSymbol(String color)
    {
        super(color);
    }

    /**
     * Retorna la etiqueta de este tipo de simbolo.
     *
     * @return siempre "N"
     */
    public String getTypeLabel()
    {
        return "N";
    }
}

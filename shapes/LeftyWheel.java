/**
 * Una rueda copiona: en vez de girar por su cuenta, cuando le toca
 * moverse simplemente copia lo que esta mostrando la rueda que tiene
 * a su izquierda, Si es la primera no hace nada.
 */
public class LeftyWheel extends Wheel
{
    private Wheel leftNeighbor;

    /**
     * Guarda cual es la rueda que esta a su izquierda en este
     * momento, para saber a quien copiarle despues.
     *
     * @param left la rueda vecina de la izquierda (o null si no hay)
     */
    @Override
    public void updateLeftNeighbor(Wheel left)
    {
        leftNeighbor = left;
    }

    /**
     * En vez de girar al azar, copia el simbolo que tiene visible
     * su vecina de la izquierda.
     */
    @Override
    public void spin()
    {
        copyFromNeighbor();
    }

    /**
     * Aunque le pidan un giro paso a paso, esta rueda prefiere copiar
     * a su vecina en vez de moverse por su cuenta un solo paso.
     *
     * @param direction se ignora, esta rueda no gira sola
     */
    @Override
    public void rotateOneStep(int direction)
    {
        copyFromNeighbor();
    }

    /**
     * Busca, entre sus propios simbolos, el mismo color que tiene
     * visible su vecina, y lo pone como su propio simbolo visible.
     */
    private void copyFromNeighbor()
    {
        if (leftNeighbor == null)
        {
            return;
        }
        String neighborColor = leftNeighbor.currentSymbol();
        if (neighborColor != null)
        {
            placeSymbol(neighborColor);
        }
    }

    /**
     * Retorna la etiqueta de este tipo de rueda.
     *
     * @return siempre "L"
     */
    public String getTypeLabel()
    {
        return "L";
    }
}

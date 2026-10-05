/**
 * Algo que se puede identificar visualmente con una etiqueta corta.
 */
public interface Labeled
{
    /**
     * Da una etiqueta corta que identifica el tipo concreto del
     * objeto (por ejemplo "N" para normal, "L" para lefty).
     *
     * @return una letra o palabra corta que identifica el tipo
     */
    String getTypeLabel();
}

/**
 * Una rueda que no se deja mandar: gira normal (al azar), pero no
 * acepta que la fijen, ni que la intercambien con otra, ni que la
 * eliminen de la maquina.
 */
public class RebelWheel extends Wheel
{
    /**
     * Gira la rueda y deja un simbolo cualquiera visible, igual que
     * una rueda normal.
     */
    @Override
    public void spin()
    {
        if (!symbols.isEmpty())
        {
            setCurrentPosition((int) (Math.random() * symbols.size()));
        }
    }

    /**
     * Esta rueda ignora cualquier intento de fijarla: no pasa nada.
     */
    @Override
    public void lock()
    {
        // una rueda rebelde no se deja fijar, asi que este metodo no hace nada
    }

    /**
     * Esta rueda nunca queda fija, sin importar lo que se intente.
     *
     * @return siempre false
     */
    @Override
    public boolean isLocked()
    {
        return false;
    }

    /**
     * Esta rueda no se puede eliminar de la maquina.
     *
     * @return siempre false
     */
    @Override
    public boolean allowsRemoval()
    {
        return false;
    }

    /**
     * Esta rueda no se puede intercambiar con otra.
     *
     * @return siempre false
     */
    @Override
    public boolean allowsSwap()
    {
        return false;
    }

    /**
     * Retorna la etiqueta de este tipo de rueda.
     *
     * @return siempre "R"
     */
    public String getTypeLabel()
    {
        return "R";
    }
}

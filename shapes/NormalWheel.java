/**
 * La rueda de por defecto, actua como una rueda corriente.
 *
 */
public class NormalWheel extends Wheel
{
    /**
     * Gira la rueda y deja un simbolo cualquiera visible.
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
     * Retorna la etiqueta de este tipo de rueda.
     *
     * @return siempre "N"
     */
    public String getTypeLabel()
    {
        return "N";
    }
}

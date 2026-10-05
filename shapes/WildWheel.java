/**
 * El tipo de rueda nuevo que propusimos para este ciclo es una rueda
 * terca. Si le indicas que avance 1, avanza 2.
 */
public class WildWheel extends Wheel
{
    private static final int EXTRA_FACTOR = 2;

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
     * Avanza (o retrocede) el doble de pasos de lo que normalmente
     * avanzaria una rueda comun.
     *
     * @param direction 1 para avanzar, -1 para retroceder
     */
    @Override
    public void rotateOneStep(int direction)
    {
        for (int i = 0; i < EXTRA_FACTOR; i++)
        {
            super.rotateOneStep(direction);
        }
    }

    /**
     * Retorna la etiqueta de este tipo de rueda.
     *
     * @return siempre "W"
     */
    public String getTypeLabel()
    {
        return "W";
    }
}

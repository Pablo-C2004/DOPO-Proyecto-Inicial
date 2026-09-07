
/**
 * Representa un simbolo de la maquina tragamonedas, cada simbolo se identifica por
 * un color en CSS.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Symbol
{
    
    private String color;

    /**
     * Crea un simbolo nuevo con el color que se indica
     */
    public Symbol(String color)
    {
        this.color = color;
    }

    /**
     * Retorna el color del simbolo
     * 
     * @return     color del simbolo
     */
    public String getColor()
    {
        return color;
    }
}
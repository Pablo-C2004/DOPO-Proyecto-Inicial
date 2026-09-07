import java.util.ArrayList;
/**
 * Representa una rueda de la maquina tragamonedas
 * Una rueda se representa con una lista ordenada de simbolos e identifica cual esta
 * ahora
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Wheel
{
    private ArrayList<Symbol> symbols;
    private int currentPosition;
    private boolean locked;

    /**
     * Crea una rueda vacia.
     */
    public Wheel()
    {
        symbols = new ArrayList<Symbol>();
        currentPosition = 0;
        locked = false;
    }

    /**
     * Agrega un simbolo nuevo en la posicion que indique la rueda
     * Si la posicion es menor a 1, se usa la posicion 1 y si es mayor a el numero
     * de simbolos del array se usa la ultima posible.
     * 
     * @param  int posicion donde se inserta
     * @param  color color del nuevo simbolo
     */
    public void addSymbol(int pos, String color)
    {
        int max = symbols.size() + 1;
        int index = normalize(pos, max);
        symbols.add(index - 1, new Symbol(color));
    }
    
    /**
     * Elimina el simbolo del color indicado, si existe en la rueda.
     * 
     * @param color color del simbolo a eliminar
     * @return true si el simbolo existia y fue eliminado
     */
    public boolean delSymbol(String color)
    {
        for (int i = 0; i < symbols.size(); i++)
        {
            if (symbols.get(i).getColor().equals(color))
            {
                symbols.remove(i);
                if (currentPosition >= symbols.size())
                {
                    currentPosition = Math.max(0, symbols.size() -1);
                }
                return true;
            }
        }
            return false;
    }
    /**
     * Hace que la rueda muestre el simbolo del color indicado
     * 
     * @param color color del simbolo que se quiere mostrar
     * @return true si el simbolo existe en la rueda
     */
    public boolean placeSymbol(String color)
    {
        for (int i = 0; i < symbols.size(); i++)
        {
            if(symbols.get(i).getColor().equals(color))
            {
                currentPosition = i;
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gira la rueda: Elige al azar uno de los simbolos.
     */
    public void spin()
    {
        if (!symbols.isEmpty())
        {
            currentPosition = (int)(Math.random() * symbols.size());
        }
    }
    
    /**
     * Rota la rueda exactamente un paso, hacia adelante o hacia atras.
     * Si llega al final de la lista, continua desde el principio (y
     * al reves), simulando una rueda real.
     *
     * @param direction 1 para avanzar un paso, -1 para retroceder uno
     */
    public void rotateOneStep(int direction)
    {
        if (symbols.isEmpty())
        {
            return;
        }
        currentPosition = (currentPosition + direction + symbols.size()) % symbols.size();
    }
 
    /**
     * Fija la rueda: mientras este fija no debe girar.
     */
    public void lock()
    {
        locked = true;
    }
 
    /**
     * Suelta la rueda, si estaba fija.
     */
    public void unlock()
    {
        locked = false;
    }
 
    /**
     * Indica si la rueda esta fija actualmente.
     *
     * @return true si la rueda esta fija
     */
    public boolean isLocked()
    {
        return locked;
    }
 
    /**
     * Indica si esta rueda tiene un simbolo del color indicado.
     *
     * @param color color a buscar
     * @return true si la rueda tiene ese color entre sus simbolos
     */
    public boolean hasSymbol(String color)
    {
        for (int i = 0; i < symbols.size(); i++)
        {
            if (symbols.get(i).getColor().equals(color))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Retorna el color del simbolo que esta visible en este momento.
     * 
     * @return el color visible o null si la rueda está vacia.
     */
    public String currentSymbol()
    {
        if (symbols.isEmpty())
        {
            return null;
        }
        return symbols.get(currentPosition).getColor();
    }
    
    /**
     * Retorna los colores de todos los simbolos de la rueda, en el orden
     * en que estan ubicados
     * 
     * @return arreglo con los colores de los simbolos
     */
    public String[] symbolsList()
    {
        String[] result = new String[symbols.size()];
        for (int i = 0; i < symbols.size(); i++)
        {
            result[i] = symbols.get(i).getColor();
        }
        return result;
    }
    
    /**
     * Ajusta la posicion que se ingresa para que entre dentro del rango valido.
     * 
     * @param int posicion pedida
     * @param max valor permitido
     * @return la posicion ajustada
     */
    private int normalize(int pos, int max)
    {
        if (pos < 1)
        {
            return 1;
        }
        if (pos > max)
        {
            return max;
        }
        return pos;
    }
}
import java.util.ArrayList;

/**
 * Representa, en general, una rueda de la maquina tragamonedas. 
 */
public abstract class Wheel implements Labeled
{
    protected ArrayList<Symbol> symbols;
    protected int currentPosition;
    protected boolean locked;

    /**
     * Crea una rueda vacia, sin simbolos todavia y sin estar fija.
     */
    public Wheel()
    {
        symbols = new ArrayList<Symbol>();
        currentPosition = 0;
        locked = false;
    }

    /**
     * Agrega un simbolo ya construido en la posicion indicada.
     *
     * @param pos posicion donde se inserta (empieza en 1)
     * @param symbol el simbolo a agregar
     */
    public void addSymbol(int pos, Symbol symbol)
    {
        int max = symbols.size() + 1;
        int index = normalize(pos, max);
        symbols.add(index - 1, symbol);
    }

    /**
     * Elimina el simbolo del color indicado, si existe en esta rueda.
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
                    currentPosition = Math.max(0, symbols.size() - 1);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Hace que la rueda muestre el simbolo del color indicado.
     *
     * @param color color del simbolo que se quiere mostrar
     * @return true si el simbolo existe en esta rueda
     */
    public boolean placeSymbol(String color)
    {
        for (int i = 0; i < symbols.size(); i++)
        {
            if (symbols.get(i).getColor().equals(color))
            {
                setCurrentPosition(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Mueve la rueda exactamente un paso, hacia adelante o hacia
     * atras.
     *
     * @param direction 1 para avanzar un paso, -1 para retroceder uno
     */
    public void rotateOneStep(int direction)
    {
        if (symbols.isEmpty())
        {
            return;
        }
        setCurrentPosition((currentPosition + direction + symbols.size()) % symbols.size());
    }

    /**
     * Gira la rueda. Cada tipo de rueda decide que significa
     * girar: una normal elige un simbolo al azar, pero
     * una rueda lefty no elige al azar sino que copia a
     * la rueda que tiene a su izquierda.
     */
    public abstract void spin();

    /**
     * Le avisa a la rueda cual es, en este momento, la rueda que
     * tiene a su izquierda o null si es la primera de todas.
     *
     * @param left la rueda que esta inmediatamente a la izquierda
     */
    public void updateLeftNeighbor(Wheel left)
    {
        // las ruedas lefty necesitan saber que tienen a su izquierda
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
     * Indica si esta rueda se puede eliminar de la maquina.
     *
     * @return true si se permite eliminar esta rueda
     */
    public boolean allowsRemoval()
    {
        return true;
    }

    /**
     * Indica si esta rueda se puede intercambiar con otra
     *
     * @return true si se permite intercambiar esta rueda
     */
    public boolean allowsSwap()
    {
        return true;
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
     * Retorna el objeto Symbol que esta visible en este momento.
     *
     * @return el simbolo visible, o null si la rueda no tiene simbolos
     */
    public Symbol getCurrentSymbol()
    {
        if (symbols.isEmpty())
        {
            return null;
        }
        return symbols.get(currentPosition);
    }

    /**
     * Retorna el color del simbolo que esta visible en este momento.
     *
     * @return el color visible, o null si la rueda no tiene simbolos
     */
    public String currentSymbol()
    {
        Symbol current = getCurrentSymbol();
        if (current == null)
        {
            return null;
        }
        return current.getColor();
    }

    /**
     * Retorna los colores de todos los simbolos de la rueda, en el
     * orden en que estan ubicados, iniciando por el 1.
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
     * Cambia cual es la posicion visible, y le avisa al simbolo que
     * queda visible que fue seleccionado.
     *
     * @param index la nueva posicion visible
     */
    protected void setCurrentPosition(int index)
    {
        currentPosition = index;
        Symbol current = getCurrentSymbol();
        if (current != null)
        {
            current.onSelected();
        }
    }

    /**
     * Ajusta una posicion pedida para que quede dentro del rango
     * valido (1, max).
     *
     * @param pos posicion pedida
     * @param max valor maximo permitido
     * @return la posicion ya ajustada
     */
    protected int normalize(int pos, int max)
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

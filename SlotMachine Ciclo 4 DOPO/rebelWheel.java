
/**
 * Write a description of class rebelWheel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class rebelWheel extends Wheel
{
    public final boolean locked = false;

    /**
     * Constructor for objects of class rebelWheel
     */
    public rebelWheel(String initialSymbol)
    {
        super(initialSymbol, "yellow");
    }

    public void lock(){
    }

    public void unlock(){
    }
}
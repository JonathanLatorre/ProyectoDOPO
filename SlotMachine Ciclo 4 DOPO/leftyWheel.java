
/**
 * Write a description of class leftyWheel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class leftyWheel extends Wheel
{
    public Wheel lefty;

    /**
     * Constructor for objects of class leftyWheel
     */
    public leftyWheel(String initialSymbol)
    {
        super(initialSymbol, "blue");
    }
    public void setLefty(Wheel lefty){
        this.lefty = lefty;
        this.copyLefty();

    }

    public void copyLefty(){
        if (lefty != null){
            String leftySymbol = lefty.getSymbol();
            super.setSymbol(leftySymbol);
        }
    }
    @Override
    public String getSymbol() {
        if (lefty != null) {
            copyLefty();
        }
        return super.getSymbol();
    }
}
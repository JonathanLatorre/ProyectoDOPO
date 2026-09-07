
/**
 * Write a description of class Wheel here.
 * 
 * @author DeLaPeña-Latorre 
 * @version 1.0
 */
public class Wheel
{
    
    private Rectangle frame;
    private Circle symbol;
    private String currentSymbol;
    private boolean locked;

    public Wheel(String initialSymbol) {
        this.currentSymbol = initialSymbol;
        locked = false;
        
        frame = new Rectangle();
        frame.changeSize(55, 45);
        frame.changeColor("white");

        symbol = new Circle();
        symbol.changeSize(28);
        symbol.changeColor(initialSymbol);
    }
    /**
     * Return the symbol of a given wheel
     * @return currentSymbol currentSymbol of the given wheel
     */
    public String getSymbol(){
        return currentSymbol;
    }
    /**
     * Changes the currentSymbol of a wheel
     * @param newColor it's the new symbol that the wheel is gonna have.
     */
    public void setSymbol(String newColor){
        this.currentSymbol = newColor;
        symbol.changeColor(newColor);
    }
    /**
     * Resize and relocates the frame and cirlce to an available space.
     */
    public void relocate(int x, int y, int frameWidth, int frameHeight, int circleSize) {
        frame.changeSize(frameHeight, frameWidth);
        frame.moveTo(x, y);

        int circleX = x + (frameWidth - circleSize) / 2;
        int circleY = y + (frameHeight - circleSize) / 2;
        symbol.changeSize(circleSize);
        symbol.moveTo(circleX, circleY);
    }

    public void makeVisible() {
        frame.makeVisible();
        symbol.makeVisible();
    }
    public void makeInvisible(){
        frame.makeInvisible();
        symbol.makeInvisible();
    }
    
    /**
     * Bloquea una rueda para evitar modificaciones.
     */
    public void lock(){
        locked = true;
    }

    /**
     * Desbloquea una rueda.
     */
    public void unlock(){
        locked = false;
    }

    /**
     * Informa si la rueda se encuentra bloqueada.
     *
     * @return true si esta bloqueada.
     */
    public boolean isLocked(){
        return locked;
    }

}
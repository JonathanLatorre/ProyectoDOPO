public class Wheel {
    
    private Rectangle frame;
    private Symbol symbol;
    private boolean locked;

    public Wheel(String initialSymbol) {
        this.locked = false;
        
        this.frame = new Rectangle();
        this.frame.changeSize(55, 45);
        this.frame.changeColor("white");

        this.symbol = new Symbol(initialSymbol, 28);
    }

    public String getSymbol() {
        return symbol.getColor();
    }

    public void setSymbol(String newColor) {
        symbol.setColor(newColor);
    }

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

    public void makeInvisible() {
        frame.makeInvisible();
        symbol.makeInvisible();
    }
    
    public void lock() {
         locked = true; 
    }
    public void unlock() {
    locked = false; 
    }
    public boolean isLocked() {
    return locked; 
    }
}
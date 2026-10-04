public abstract class Wheel {
    public Rectangle frame;
    public Symbol symbol;
    public boolean locked;

    public Wheel(String initialSymbol, String colorFrame) {
        this.locked = false;
        this.frame = new Rectangle();
        this.frame.changeSize(160, 100);
        this.frame.changeColor(colorFrame);

        this.symbol = new Symbol(initialSymbol, 90);
    }

    public String getSymbol() {
        return symbol.getColor();
    }

    public void setSymbol(String newColor) {
        symbol.setColor(newColor);
    }

    public void relocate(int x, int y, int frameWidth, int frameHeight, int circleSize){
        frame.changeSize(frameHeight, frameWidth);
        frame.moveTo(x, y);
        int circleX = x + (frameWidth - circleSize) / 2;
        int circleY = y + (frameHeight - circleSize) / 2;
        symbol.changeSize(circleSize);
        symbol.moveTo(circleX, circleY);
    }

    public final void makeVisible() {
        frame.makeVisible();
        symbol.makeVisible();
    }

    public final void makeInvisible() {
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
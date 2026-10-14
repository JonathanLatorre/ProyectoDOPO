public abstract class Wheel {
    public Rectangle frame;
    public Symbol symbol;
    public boolean locked;

    private int frameX;
    private int frameY;
    private int frameWidth = 100;
    private int frameHeight = 160;

    public Wheel(String initialSymbol, String colorFrame) {
        this.locked = false;
        this.frame = new Rectangle();
        this.frame.changeSize(160, 100);
        this.frame.changeColor(colorFrame);



        this.symbol = new normalSymbol(initialSymbol, 90);
    }

    public String getSymbol() {
        return symbol.getColor();
    }

    public void setSymbol(String newColor) {
        if (this.symbol instanceof ephemeralSymbol){
            ((ephemeralSymbol)this.symbol).shrink();
        }
        
        symbol.setColor(newColor);
        if (this.symbol instanceof shySymbol) {
            ((shySymbol) this.symbol).applyVisibility();
        }
    }

    public void relocate(int x, int y, int frameWidth, int frameHeight, int circleSize) {
        this.frameX = x;
        this.frameY = y;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;

        frame.changeSize(frameHeight, frameWidth);
        frame.moveTo(x, y);

        if (symbol != null) {
            if (!(symbol instanceof ephemeralSymbol) || symbol.getSize() > circleSize) {
                symbol.changeSize(circleSize);
            }
            
            int actualSize = symbol.getSize();
            int circleX = x + (frameWidth - actualSize) / 2;
            int circleY = y + (frameHeight - actualSize) / 2;
            symbol.moveTo(circleX, circleY);
        }
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
    public void setSymbol(Symbol newSymbol) {
        if (newSymbol == null) return;
    

        if (this.symbol != null) {
            this.symbol.makeInvisible();
        }
    
        this.symbol = newSymbol;
    
        int actualSize = this.symbol.getSize();
        int circleX = this.frameX + (this.frameWidth - actualSize) / 2;
        int circleY = this.frameY + (this.frameHeight - actualSize) / 2;
        this.symbol.moveTo(circleX, circleY);
    

        if (this.symbol instanceof shySymbol) {
            ((shySymbol) this.symbol).toggleVisibility();
        } else if (this.frame.isVisible()) {
            this.symbol.makeVisible();
        }
    }

    public Symbol getSymbolObject() {
        return symbol;
    }
}
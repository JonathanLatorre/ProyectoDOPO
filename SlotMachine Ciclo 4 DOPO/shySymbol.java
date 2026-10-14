import java.util.HashMap;

/**
 * Write a description of class shySymbol here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class shySymbol extends Symbol {
    private static HashMap<String, Boolean> hiddenByColor = new HashMap<String, Boolean>();
    private boolean isCurrentlyHidden;

    public shySymbol(String color) {
        super(color); 
        HiddenState();
    }
    public shySymbol(String color, int size) {
        super(color, size);
        HiddenState();
    }

    @Override
    public void changeSize(int newSize) {
        if (newSize >= 0) {
            this.size = newSize;
            this.shape.changeSize(newSize);
        }
    }

    /**
     * Called each time this shy color lands on a wheel.
     */
    public void toggleVisibility() {
        this.isCurrentlyHidden = !this.isCurrentlyHidden;
        hiddenByColor.put(this.color, this.isCurrentlyHidden);
        applyVisibility();
    }

    public void applyVisibility() {
        if (this.isCurrentlyHidden) {
            super.makeInvisible();
        } else {
            super.makeVisible();
        }
    }

    /**
     * A wheel may ask every symbol to become visible. Shy symbols ignore that
     * while they are in a hidden turn.
     */
    @Override
    public void makeVisible() {
        if (this.isCurrentlyHidden) {
            super.makeInvisible();
        } else {
            super.makeVisible();
        }
    }

    public boolean isCurrentlyHidden() {
        return isCurrentlyHidden;
    }

    private void HiddenState() {
        if (hiddenByColor.containsKey(this.color)) {
            this.isCurrentlyHidden = hiddenByColor.get(this.color);
        } else {
            this.isCurrentlyHidden = false;
        }
    }

}


/**
 * Write a description of class stickySymbol here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class stickySymbol extends Symbol {

    private boolean stuck;

    public stickySymbol(String color) {
        super(color);
        stuck = true;
    }

    public stickySymbol(String color, int size) {
        super(color, size);
        stuck = true;
    }

    @Override
    public void changeSize(int newSize) {
        if (newSize >= 0) {
            this.size = newSize;
            this.shape.changeSize(newSize);
        }
    }

    /**
     * Retorna si el símbolo sigue pegado.
     */
    public boolean isStuck() {
        return stuck;
    }

    /**
     * Consume el estado pegado.
     */
    public void unstick() {
        stuck = false;
    }

    /**
     * Vuelve a quedar pegado.
     */
    public void stick() {
        stuck = true;
    }
}
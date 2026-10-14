public class ephemeralSymbol extends Symbol {
    private static int lastSize = -1;
    private int originalSize;

    public ephemeralSymbol(String color) {
        super(color);
        this.originalSize = this.size;
        loadSizeState();
    }

    public ephemeralSymbol(String color, int size) {
        super(color, size);
        this.originalSize = size;
        loadSizeState();
    }

    @Override
    public void changeSize(int newSize) {
        if (newSize >= 0) {
            this.size = newSize;
            this.shape.changeSize(newSize);
            lastSize = this.size;
        }
    }

    /**
     * Decreases the symbol size by a step (e.g., when spun).
     */
    public void shrink() {
        int newSize = Math.max(0, this.size - 10);
        changeSize(newSize);
    }

    private void loadSizeState() {
        if (lastSize >= 0) {
            changeSize(lastSize);
        }
    }
}

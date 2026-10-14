/**
 * Write a description of class normalSymbol here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class normalSymbol extends Symbol
{

    /**
     * Constructor for objects of class normalSymbol
     * @param color a String that determines the color of the symbol
     */
    public normalSymbol(String color)
    {
        super(color);
    }

    public normalSymbol(String color, int size){
        super(color, size);
    }
    @Override
    /**
     * Cambia el tamaño del símbolo en la pantalla.
     * @param newSize nuevo diámetro en píxeles.
     */
    public void changeSize(int newSize) {
        if (newSize >= 0) {
            this.size = newSize;
            this.shape.changeSize(newSize);
        }
    }
}
import java.awt.*;

/**
 * Representa un símbolo visual e individual utilizado en las ruedas de la máquina tragamonedas.
 * Encapsula tanto su identificación/color como su representación gráfica (Circle).
 * 
 * @author De La Peña - Latorre
 * @version 1.0 (2026)
 */
public class Symbol {
    
    private String color;
    private Circle shape;
    private int size;
    private int xPosition;
    private int yPosition;
    private boolean isVisible;

    /**
     * Crea un nuevo símbolo a partir de un color especificado.
     * @param color el color/nombre del símbolo (ej. "red", "blue", "green", "yellow", "magenta", "black").
     */
    public Symbol(String color) {
        this(color, 28);
    }

    /**
     * Crea un nuevo símbolo indicando su color y su tamaño en píxeles.
     * @param color el color/nombre del símbolo.
     * @param size diámetro del círculo representativo en píxeles.
     */
    public Symbol(String color, int size) {
        this.color = (color != null) ? color.toLowerCase().trim() : "black";
        this.size = size;
        this.isVisible = false;
        
        this.shape = new Circle();
        this.shape.changeSize(this.size);
        this.shape.changeColor(this.color);
    }

    /**
     * Obtiene el color/nombre asociado a este símbolo.
     * @return el identificador del color en minúsculas.
     */
    public String getColor() {
        return color;
    }

    /**
     * Cambia el color del símbolo y actualiza su figura visual.
     * @param newColor nuevo color para el símbolo.
     */
    public void setColor(String newColor) {
        if (newColor != null && !newColor.trim().isEmpty()) {
            this.color = newColor.toLowerCase().trim();
            this.shape.changeColor(this.color);
        }
    }

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

    /**
     * Reubica el símbolo en unas coordenadas específicas.
     * @param newX posición horizontal en píxeles.
     * @param newY posición vertical en píxeles.
     */
    public void moveTo(int newX, int newY) {
        this.xPosition = newX;
        this.yPosition = newY;
        this.shape.moveTo(newX, newY);
    }

    /**
     * Hace visible la representación gráfica del símbolo.
     */
    public void makeVisible() {
        this.isVisible = true;
        this.shape.makeVisible();
    }

    /**
     * Oculta la representación gráfica del símbolo.
     */
    public void makeInvisible() {
        this.isVisible = false;
        this.shape.makeInvisible();
    }

    /**
     * Indica si el símbolo está visible actualmente.
     * @return true si es visible, false en caso contrario.
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Compara si dos símbolos son iguales comparando su color.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;   
        }
        if (obj == null){
        return false;
        }
        if (obj instanceof Symbol) {
            Symbol other = (Symbol) obj;
            return this.color.equalsIgnoreCase(other.color);
        }
        if (obj instanceof String) {
            return this.color.equalsIgnoreCase((String) obj);
        }
        return false;
    }

    @Override
    public String toString() {
        return this.color;
    }
}
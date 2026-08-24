import javax.swing.*; 
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Simulador de Máquina Tragamonedas (Slot Machine).
 * Permite gestionar ruedas, símbolos, giros y representación gráfica en Canvas.
 * 
 * @author De La Peña - Latorre
 * @version 1.0 (2026)
 */
public class SlotMachine {

    private ArrayList<Integer> wheels;
    private ArrayList<String> symbols;
    private ArrayList<String> currentSymbols;
    private boolean isVisible;
    private boolean lastOk;

    // Componentes gráficos
    private Rectangle body;
    private Rectangle screenArea;
    private Rectangle jackpotLight;
    private Triangle roof;
    private ArrayList<Rectangle> wheelFrames;
    private ArrayList<Circle> wheelSymbols;

    public SlotMachine() {
        String[] colors = {"red","blue","cyan","dark_gray","green","magenta","orange","pink","purple","yellow"};
        wheels = new ArrayList<Integer>();
        symbols = new ArrayList<String>();
        currentSymbols = new ArrayList<String>();
        wheelFrames = new ArrayList<Rectangle>();
        wheelSymbols = new ArrayList<Circle>();
        isVisible = false;
        lastOk = true;
        for (int i = 0; i < 3; i++) { 
            wheels.add(i + 1); 
            currentSymbols.add(colors[i]);
            symbols.add(colors[i]);
            // Componente grafico de las ruedas
            Rectangle frame= new Rectangle();
            frame.changeSize(55,45);
            frame.changeColor("white");
            wheelFrames.add(frame); 
            
            Circle circle= new Circle();
            circle.changeSize(28);
            circle.changeColor(colors[i]);
            wheelSymbols.add(circle);
        }
        // Componentes graficos
        body = new Rectangle();
        body.changeSize(120, 220);
        body.changeColor("black");
        body.moveHorizontal(-50);
        body.moveVertical(40);

        screenArea = new Rectangle();
        screenArea.changeSize(70, 200);
        screenArea.changeColor("yellow");
        screenArea.moveHorizontal(-40);
        screenArea.moveVertical(70);

        jackpotLight = new Rectangle();
        jackpotLight.changeSize(15, 60);
        jackpotLight.changeColor("red");
        jackpotLight.moveHorizontal(30);
        jackpotLight.moveVertical(45);

        roof = new Triangle();
        roof.changeSize(30, 100);
        roof.changeColor("red");
        roof.moveTo(60, 25);
        
        updateVisualPositions();
    }
    
    private int normalizePosition(int pos,int max){
        if (pos <= 1) {
            return 0;
        }else if(pos>50){
            pos = max;
        }
        return pos-1;
    }
    
    /**
     * Añade una rueda a la maquina en una posicion dada
     * 
     * @param pos es un entero quedetermina la posicion en la que se pondra la nueva rueda 1&le; pos &le; cantidad de ruedas
     */
    public void addWheel(int pos){
        int posi= normalizePosition(pos, wheels.size());
        
        wheels.add(posi,wheels.size()+1);
        currentSymbols.add(posi,symbols.get(0));
        
        Rectangle frame = new Rectangle();
        frame.changeSize(55, 45);
        frame.changeColor("white");
        wheelFrames.add(posi, frame);

        Circle circle = new Circle();
        circle.changeSize(28);
        circle.changeColor(currentSymbols.get(posi));
        wheelSymbols.add(posi, circle);
        
        updateVisualPositions();
        checkJackpot();
        lastOk = true;
    }

    /**
     * Elimina una rueda en una posicion en especifico
     * 
     * @param pos es un entero que determina la rueda que sera eliminada 1 &le; pos &le; cantidad de ruedas
     */
    public void deleteWheel(int pos){
        int posi = normalizePosition(pos,wheels.size()+1);
        if (wheels.size()<=2){
            if (isVisible){
                JOptionPane.showMessageDialog(null,"Ruedas minimas alcanzadas",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            }
            lastOk = false;
            return;
        }
                        
        wheelFrames.get(posi).makeInvisible();
        wheelSymbols.get(posi).makeInvisible();
        
        
        wheels.remove(posi);
        currentSymbols.remove(posi);
        wheelFrames.remove(posi);
        wheelSymbols.remove(posi);
            
        updateVisualPositions();
        checkJackpot();
        lastOk=true;            
    }
    
    /**
     * Añade un símbolo a la paleta disponible.
     * @param pos posicion en la que se colocara el nuevo simbolo, su rango es 1 &le; pos &le; cantidad de ruedas
     * @param color el color que se desea para el nuevo simbolo, tiene que ser: "red","blue","cyan","dark_gray","green","magenta","orange","pink","purple","yellow". No pueden repetirse colores
     */
    public void addSymbol(int pos, String color) {
        if (color == null || color.isEmpty()) {
            lastOk = false;
            return;
        }
        int posi = normalizePosition(pos, symbols.size());
        String formatColor= color.toLowerCase().trim();
        if (symbols.contains(formatColor)){
            lastOk = false;
            return;
        }
        symbols.add(posi, formatColor);
        lastOk = true;
    }
    
    /**
     * Elimina un simbolo de la maquina. Si una maquina posee el simbolo, esta pasara a tener elc olor de la posicion 0 de symbols
     * @param color el color del simbolo que se desea eliminar
     */
    public void delSymbol(String color){
        if (symbols.size() <=1){
            if (isVisible==true) {
                JOptionPane.showMessageDialog(null,
                "No se pueden eliminar todos los simbolos",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            }
            lastOk = false;
            return;
        }
        boolean removed = symbols.remove(color.toLowerCase());
        if (removed == false){
            lastOk = false;
            return;
        }
        for (int i = 0; i < currentSymbols.size(); i++) {
            if (currentSymbols.get(i).equalsIgnoreCase(color)) {
                currentSymbols.set(i, symbols.get(0));
                wheelSymbols.get(i).changeColor(symbols.get(0));
            }
        }
        checkJackpot();
        lastOk = true;    
    }
    
    /**
     * Gira una rueda en especifico de la maquina una vez
     * @Param se refiere a la rueda que sera movida una vez
     */
    public void spin(int wheel){
        if (wheels.size()==0){
            if (isVisible==true){
            JOptionPane.showMessageDialog(null,
            "no se ha iniciado la tragaperras",
            "Advertencia",
            JOptionPane.WARNING_MESSAGE);
            lastOk = false;
            return;
            }
        }
        int posi= normalizePosition(wheel, wheels.size()-1);
        int posCurrentSymbol = symbols.indexOf(currentSymbols.get(posi));
        int posNextCurrentSymbol = (posCurrentSymbol + 1) %  symbols.size();
        currentSymbols.set(posi, symbols.get(posNextCurrentSymbol));
        wheelSymbols.get(posi).changeColor(currentSymbols.get(posi));
        checkJackpot();
    }
    
    /**
     * Gira todas las ruedas una vez
     */
    public void spin(){
        for (int i=1 ;i <wheels.size();i++){
            spin(i);
        }
    }
    
    /**
     * Retorna los símbolos disponibles en la máquina.
     * @return lista de símbolos registrados.
     */
    public ArrayList<String> symbols(){
        return new ArrayList<String>(symbols);
    }
    
    /**
     * Retorna la cantidad de símbolos distintos visibles
     * en la configuración actual.
     *
     * @return cantidad de símbolos distintos.
     */
    public int distinctSymbols(){
        ArrayList<String> distinct = new ArrayList<String>();
    
            for(String symbol : currentSymbols){
                if(!distinct.contains(symbol)){
                    distinct.add(symbol);
                }
            }

        return distinct.size();
    }
    
    /**
     * Retorna la configuración actual de la máquina.
     *
     * @return símbolos mostrados por cada rueda.
     */
    public ArrayList<String> configuration(){
        return new ArrayList<String>(currentSymbols);
    }
    
    /**
     * Verifica si todas las posiciones son iguales o no.
     * @return isJackpot, True si todas las posiciones poseen el mismo simbolo, False si una o mas posiciones poseen simbolos distintos
     */
    public boolean isJackpot(){
        if (currentSymbols.isEmpty()== true){
            if (isVisible==true){
                JOptionPane.showMessageDialog(null,
                "no se ha iniciado la tragaperras",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
                lastOk= false;
                return false;
            }
        }else{
            // Corrección:
            for (int i = 1; i < currentSymbols.size(); i++) {
                if (!currentSymbols.get(i).equals(currentSymbols.get(0))) {
                    return false;
                }
        }
        }
        return true;
        
    }
    
    /**
     * Hace visible a la maquina
     */
    public void makeVisible() {
        isVisible = true;
        body.makeVisible();
        screenArea.makeVisible();
        jackpotLight.makeVisible();
        roof.makeVisible();

        for (int i = 0; i < wheels.size(); i++) {
            wheelFrames.get(i).makeVisible();
            wheelSymbols.get(i).makeVisible();
        }
        checkJackpot();
        lastOk = true;
    }
    
    /**
     * Hace invisible la máquina tragamonedas.
     */
    public void makeInvisible(){
        isVisible = false;
    
        body.makeInvisible();
        screenArea.makeInvisible();
        jackpotLight.makeInvisible();
        roof.makeInvisible();
    
        for(int i = 0; i < wheels.size(); i++){
            wheelFrames.get(i).makeInvisible();
            wheelSymbols.get(i).makeInvisible();
        }
    
        lastOk = true;
    }
    
    /**
     * Termina el simulador.
     */
    public void exit(){
        makeInvisible();
        lastOk = true;
    }
    
    private void checkJackpot() {
        if (isJackpot()) {
            jackpotLight.changeColor("yellow");
            roof.changeColor("yellow");
        } else {
            jackpotLight.changeColor("red");
            roof.changeColor("red");
        }
    }
    
     /**
     * Reorganiza y distribuye visualmente las ruedas dentro del área amarilla.
     */
    private void updateVisualPositions() {
        int total = wheels.size();
        if (total == 0){ 
            return;
        }

        int areaStartX = 60;
        int usableWidth = 180;
        int spacing = usableWidth / total;

        for (int i = 0; i < total; i++) {
            int frameWidth = Math.min(40, spacing - 4);
        int frameHeight = 50;

        // Coordenada X e Y absolutas para centrar cada marco dentro de screenArea
        int frameX = areaStartX + (i * spacing) + ((spacing - frameWidth) / 2);
        int frameY = 120; // Y = 120 mantiene el marco centrado verticalmente en la pantalla (Y: 110 a 180)

        // 1. Mover y redimensionar el marco blanco existente
        Rectangle frame = wheelFrames.get(i);
        frame.changeSize(frameHeight, frameWidth);
        frame.moveTo(frameX, frameY);

        // 2. Mover y redimensionar el círculo (diámetro adaptado al marco)
        int circleSize = Math.min(24, frameWidth - 6);
        int circleX = frameX + (frameWidth - circleSize) / 2;
        int circleY = frameY + (frameHeight - circleSize) / 2;

        Circle circle = wheelSymbols.get(i);
        circle.changeSize(circleSize);
        circle.moveTo(circleX, circleY);
        circle.changeColor(currentSymbols.get(i));
        if (isVisible==true) {
            frame.makeVisible();
            circle.makeVisible();
        }
    }
}
    
    public boolean ok() {
        return lastOk;
    }
}
import javax.swing.*; 
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Simulador de Máquina Tragamonedas (Slot M    achine).
 * Permite gestionar ruedas, símbolos, giros y representación gráfica en Canvas.
 * 
 * @author De La Peña - Latorre
 * @version 1.0 (2026)
 */
public class SlotMachine {

    private ArrayList<Wheel> wheels;
    private ArrayList<String> symbols;
    private boolean isVisible;
    private boolean lastOk;

    // Componentes gráficos
    private Rectangle body;
    private Rectangle screenArea;
    private Rectangle jackpotLight;
    private Triangle roof;


    public SlotMachine() {
        String[] colors = {"red","blue","green"};
        wheels = new ArrayList<Wheel>();
        symbols = new ArrayList<String>();
        isVisible = false;
        lastOk = true;
        
        for (int i = 0; i < 3; i++) { 
            symbols.add(colors[i]);
            wheels.add(new Wheel(colors[i]));
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
        Wheel newWheel = new Wheel(symbols.get(0));
        wheels.add(posi, newWheel);
        
        if (isVisible){
            newWheel.makeVisible();
        }

        
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
        
        if (wheels.size()<=2){
            if (isVisible){
                JOptionPane.showMessageDialog(null,"Ruedas minimas alcanzadas",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            }
            lastOk = false;
            return;
        }
        int posi = normalizePosition(pos,wheels.size());                
        Wheel removed = wheels.remove(posi);
        removed.makeInvisible();
        
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
        for (Wheel wheel: wheels) {
            if (wheel.getSymbol().equals(color)) {
                wheel.setSymbol(symbols.get(0));
            }
        }
        checkJackpot();
        lastOk = true;    
    }
    
    /**
     * Intercambia la posicion de dos ruedas.
     *
     *@param wheel1 primera rueda
     * @param wheel2 segunda rueda
     */
    public void swap(int wheel1, int wheel2){
        int pos1 = normalizePosition(wheel1, wheels.size());
        int pos2 = normalizePosition(wheel2, wheels.size());
    
        Wheel temp = wheels.get(pos1);
        wheels.set(pos1, wheels.get(pos2));
        wheels.set(pos2, temp);
    
        updateVisualPositions();
    
        checkJackpot();
        lastOk = true;
    }
    
    /**
     * Bloquea una rueda.
     *
     * @param wheel rueda a bloquear
     */
    public void lock(int wheel){
        int posi = normalizePosition(wheel, wheels.size());
    
        wheels.get(posi).lock();
    
        lastOk = true;
    }
    
    /**
     * Desbloquea una rueda.
     *
     * @param wheel rueda a desbloquear
     */
    public void unlock(int wheel){
        int posi = normalizePosition(wheel, wheels.size());
    
        wheels.get(posi).unlock();
    
        lastOk = true;
    }
    
    /**
     * Coloca directamente un simbolo en una rueda dada.
     *
     * @param wheel rueda objetivo
     * @param symbol simbolo a colocar
     */
    public void placeSymbol(int wheel, String symbol){
        int posi = normalizePosition(wheel, wheels.size());
    
        symbol = symbol.toLowerCase();
    
        if (!symbols.contains(symbol)){
            lastOk = false;
            return;
        }
    
        Wheel target = wheels.get(posi);
    
        if (target.isLocked()){
            lastOk = false;
            return;
        }
    
        target.setSymbol(symbol);
    
        checkJackpot();
        lastOk = true;
    }
    
    
    
    /**
     * Gira una rueda en especifico de la maquina una vez
     * @Param se refiere a la rueda que sera movida una vez
     */
    public void spin(int wheel) {
        if (wheels.isEmpty() || symbols.isEmpty()) {
            lastOk = false;
            return;
        }
        int posi = normalizePosition(wheel, wheels.size());
        Wheel targetWheel = wheels.get(posi);
        
        //CAMBIO CICLO 2 para no girar las ruedas bloqueadas
        if(targetWheel.isLocked()){
            lastOk = false;
            return;
        }
        
        int currentIndex = symbols.indexOf(targetWheel.getSymbol());
        int nextIndex = (currentIndex + 1) % symbols.size();
        
        targetWheel.setSymbol(symbols.get(nextIndex));
        checkJackpot();
        lastOk = true;
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
     * Gira una rueda una cantidad determinada de pasos.
     *
     * @param wheel rueda a girar
     * @param steps cantidad de pasos
     */
    public void spin(int wheel, int steps){
    
        int posi = normalizePosition(wheel, wheels.size());
    
        Wheel target = wheels.get(posi);

        if (target.isLocked()){
            lastOk = false;
            return;
        }
    
        for(int i = 0; i < steps; i++){
    
            spin(wheel);
    
            if(isVisible){
                Canvas.getCanvas().wait(100);
            }
        }
    
        lastOk = true;
    }
    
    /**
     * Lleva la maquina a una configuracion dada.
     *
     * @param configuration configuracion deseada
     */
    public void spin(String[] configuration){
    
        if(configuration.length != wheels.size()){
            lastOk = false;
            return;
        }
    
        for(int i = 0; i < wheels.size(); i++){
    
            Wheel current = wheels.get(i);
    
            if(current.isLocked()){
                continue;
            }
    
            String desired = configuration[i].toLowerCase();
    
            if(!symbols.contains(desired)){
                lastOk = false;
                return;
            }
    
            while(!current.getSymbol().equals(desired)){
    
                spin(i + 1);
    
                if(isVisible){
                    Canvas.getCanvas().wait(100);
                }
            }
        }
    
        checkJackpot();
    
        lastOk = true;
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
    
        for(Wheel wheel: wheels){
            if(!distinct.contains(wheel.getSymbol())){
                distinct.add(wheel.getSymbol());
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
        ArrayList<String> config = new ArrayList<String>();
        for (Wheel wheel: wheels){
            config.add(wheel.getSymbol());
        }
        return config;
    }
    
    /**
     * Verifica si todas las posiciones son iguales o no.
     * @return isJackpot, True si todas las posiciones poseen el mismo simbolo, False si una o mas posiciones poseen simbolos distintos
     */
    public boolean isJackpot(){
        if (wheels.isEmpty()){
            if (isVisible){
                JOptionPane.showMessageDialog(null,
                "no se ha iniciado la tragaperras",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
                lastOk= false;
                return false;
            }
        }else{
            String first = wheels.get(0).getSymbol();
            for(Wheel wheel : wheels){
                if (!wheel.getSymbol().equals(first)){
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

        for (Wheel wheel:wheels) {
            wheel.makeVisible();
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
    
        for(Wheel wheel:wheels){
            wheel.makeInvisible();    
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
            int frameX = areaStartX + (i * spacing) + ((spacing - frameWidth) / 2);
            int frameY = 120;
            int circleSize = Math.min(24, frameWidth - 6);

            Wheel wheel = wheels.get(i);
            wheel.relocate(frameX, frameY, frameWidth, frameHeight, circleSize);
            if (isVisible) {
                wheel.makeVisible();
            }
        }
    }    
    public boolean ok() {
        return lastOk;
    }
}
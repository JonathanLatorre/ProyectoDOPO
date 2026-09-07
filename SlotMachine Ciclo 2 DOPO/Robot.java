
/**
 * Write a description of class Robot here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Robot
{
    
    private int x;
    private int y;
    private char direction;
    private int step;
    private boolean isVisible;
    private boolean isMoveOk;
    
    private Circle body;
    private Triangle antenna;
    //Ciclo 1//
    /**
     * Constructor de robot, crea  un robot en una posicion x,y
     * @param x Posición horizontal inicial.
     * @param y Posición vertical inicial.
     */
    public Robot(int xInicial, int yInicial){
        this.x = xInicial;
        this.y = yInicial;
        this.direction  = 'N';
        this.isVisible = false;
        this.isMoveOk = true;
        //Creacion y configuracion del robot
        body = new Circle();
        antenna = new Triangle();
        
        body.changeSize(15);
        antenna.changeSize(10,10);
        antenna.changeColor("red");
        body.moveTo(x,y);
        antenna.moveTo(x,y-1);
        
    }
    /** 
     * Retorna las coordenadas actuales del robot en formato (x,y)
     */
    public String coordinates() {
        return "("+ x +","+y+")";
    }
    /**
     * Retorna la direccion hacia la que mira el robot ("N","S","E","W")
     * @return direction
     */
    public char direction() {
        return direction;
    }
    /**
     * Mueve el robot en la direccion hacia la que esta mirando, no acepta valores negativos.
     * @param step se refiere a la cantidad de px que se movera el robot hacia una direccion dada.
     */
    public void move(int step){
        if (step<0){
            throw new IllegalArgumentException("El robot no puede caminar hacia atras");
        }
        if (y-step< 0 || x-step< 0){
            isMoveOk = false;
        }else{
            if (direction == 'N'){
                y-= step;
                body.moveVertical(-step);
                antenna.moveVertical(-step);
            }else if (direction == 'S') {
                y+=step;
                body.moveVertical(step);
                antenna.moveVertical(step);
            }else if (direction == 'E') {
                x+= step;
                body.moveHorizontal(step);
                antenna.moveHorizontal(step);
            }else if (direction == 'W') {
                x-= step;
                body.moveHorizontal(-step);
                antenna.moveHorizontal(-step);
            }
            isMoveOk = true;
        }
    }
    
    /**
     * turn cambia la direccion hacia la que observa el robot, esto a su vez cambia la direccion hacia la que se mueve.
     * @param direction un char que puede ser 'N','S','E','W'
     */
    public void turn(char newDir){
        if (newDir == 'N' || newDir == 'S' || newDir == 'E' || newDir == 'W') {
            this.direction = newDir;
            this.isMoveOk = true;
        }else{
            this.isMoveOk = false;
        }
    }
    
    /** 
     * isOk confirma si el ultimo movimiento pudo hacerse o no
     * @return isMoveOk
     */
    public boolean isOK(){
        return isMoveOk;
    }
    /**
     * Se encarga de hacer visibles al robot en el canva
     */
    public void makeVisible(){
        isVisible= true;
        body.makeVisible();
        antenna.makeVisible();
    }
    /**
     * Se encarga de hacer invisible al robot en el canva
     */
    public void makeInvisible(){
        isVisible= true;
        body.makeInvisible();
        antenna.makeInvisible();
    }
}
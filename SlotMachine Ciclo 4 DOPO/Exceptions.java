
/**
 * Write a description of class Exceptions here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Exceptions{
    public static class InvalidSymbolsQuantException extends Exception{
        public InvalidSymbolsQuantException(String message){
            super(message);
        }
    }
}
package java.lang;

/**
 *
 * @author Adam Vesecky
 */
public class Throwable {
    String message;
    
    public Throwable(String message){
        this.message = message;
    }
    
    public String getMessage(){
        return message;
    }
}

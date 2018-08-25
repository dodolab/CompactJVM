package compactjvm.exceptions;

/**
 * Exception that can be thrown anytime during the instruction processing
 *
 * @author Adam Vesecky
 */
public class LoadingException  extends Exception{
    
    public LoadingException(String exc){
        super(exc);
    }
}

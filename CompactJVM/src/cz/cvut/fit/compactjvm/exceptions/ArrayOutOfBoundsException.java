package cz.cvut.fit.compactjvm.exceptions;

/**
 * Exception that is thrown inside an instruction that operates with an array
 * @author Adam Vesecky
 */
public class ArrayOutOfBoundsException extends Exception{
    
    public ArrayOutOfBoundsException(String exc){
        super(exc);
    }
}

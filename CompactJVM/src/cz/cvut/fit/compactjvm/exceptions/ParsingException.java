package cz.cvut.fit.compactjvm.exceptions;

/**
 * Exceptions that is thrown during classfile parsing
 * @author Adam Vesecky
 */

public class ParsingException extends Exception{
    
    public ParsingException(String exc){
        super(exc);
    }
}

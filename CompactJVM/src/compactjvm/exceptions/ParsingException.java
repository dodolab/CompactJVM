package compactjvm.exceptions;

/**
 * Exceptions that is thrown during parsing of a classfile 
 * @author Adam Vesecky
 */
public class ParsingException extends Exception{
    
    public ParsingException(String exc){
        super(exc);
    }
}

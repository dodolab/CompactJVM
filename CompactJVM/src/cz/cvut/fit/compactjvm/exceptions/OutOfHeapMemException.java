package cz.cvut.fit.compactjvm.exceptions;

/**
 * Occurs when heap is full and an allocation request is made
 * @author Adam Vesecky
 */
public class OutOfHeapMemException extends Exception{
    
    public OutOfHeapMemException(){
        super();
    }
}

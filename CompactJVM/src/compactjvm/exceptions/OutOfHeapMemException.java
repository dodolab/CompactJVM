package compactjvm.exceptions;

/**
 * Occurs when the heap is full and an allocation request is made
 * @author Adam Vesecky
 */
public class OutOfHeapMemException extends Exception{
    
    public OutOfHeapMemException(){
        super();
    }
}

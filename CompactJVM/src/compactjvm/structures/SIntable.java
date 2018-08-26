
package compactjvm.structures;

/**
 * Base class for primitive objects that can be casted to int
 * 
 * @author Adam Vesecky
 */
public abstract class SIntable extends SStruct {
    
    public abstract SInt toInt();
    
    public abstract void negate();
    
}

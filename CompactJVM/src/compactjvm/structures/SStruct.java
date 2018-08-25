
package compactjvm.structures;

/**
 * Common abstract class for all structures
 * @author Adam Vesecky
 */
public abstract class SStruct {
    protected static int idCounter = 0;
    
    protected int id;
    
    public SStruct(){
        this.id = idCounter++;
    }
    
    public abstract SStruct makeCopy();
    
    public boolean isReference(){
        return false;
    }
}

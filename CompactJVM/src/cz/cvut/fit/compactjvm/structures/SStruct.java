
package cz.cvut.fit.compactjvm.structures;

/**
 * Common abstract class for all structures
 * @author Adam Vesecky
 */
public abstract class SStruct {
    public abstract SStruct makeCopy();
    
    public boolean isReference(){
        return false;
    }
}

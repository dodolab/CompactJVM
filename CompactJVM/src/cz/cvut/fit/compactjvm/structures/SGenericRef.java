package cz.cvut.fit.compactjvm.structures;

/**
 * Generic reference
 * 
 * @author Adam Vesecky
 */
public abstract class SGenericRef extends SStruct{
    // reference to the heap
    protected int heapReference;
    
    public SGenericRef(){
        // pass null
        heapReference = -1;
    }
    
    public SGenericRef(int heapReference){
        this.heapReference = heapReference;
    }
    
    public int getReference(){
        return heapReference;
    }
    
    public void setReference(int reference){
        this.heapReference = reference;
    }
    
    public boolean isNull(){
        return heapReference == -1;
    }

    @Override
    public abstract SStruct makeCopy();

    @Override
    public boolean isReference(){
        return true;
    }
}

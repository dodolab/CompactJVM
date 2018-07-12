package cz.cvut.fit.compactjvm.cpentities;

import java.io.DataInputStream;

/**
 * Constant pool entity
 * 
 * @author Adam Vesecky
 */
public abstract class CPEntity {
    public int tag; // tag item
 
    // constant pool byte index
    // usually incremental, just for Long and Double will be increased by 2
    public int byteIndex;
    
    protected CPEntity(int tag){
        this.tag = tag;
    }
    
    /**
     * Gets size of entity (according to the specification, all entities
     * have size of 1 with the exception of Long and Double that have 
     * size of 2
     * @return 
     */
    public abstract int getSize();
}

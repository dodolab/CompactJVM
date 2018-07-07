package cz.cvut.fit.compactjvm.entities;

/**
 *
 * @author Adam Vesecky
 */
public class MTHEntity {
    
    public int accessFlags;
    public int nameIndex;
    public int descriptorIndex;
    public int attributesCount;
    
    public EntAttribute[] attrs;
}

package cz.cvut.fit.compactjvm.entities;

/**
 *
 * Field info entity
 * @author Adam Vesecky
 */
public class FLEntity {
    public int accessFlags;
    public int nameIndex;
    public int descriptorIndex;
    public int attributesCount;
    
    public Attribute[] attrs; // attributes
}

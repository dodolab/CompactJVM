package cz.cvut.fit.compactjvm.classfile;

import cz.cvut.fit.compactjvm.attributes.Attribute;

/**
 *
 * Field info entity
 *
 * @author Adam Vesecky
 */
public class FLEntity {

    public int accessFlags;
    public int nameIndex;
    public int descriptorIndex;
    public int attributesCount;
    public String name;
    public String descriptor;
    public int dataFieldOffset; //index, na jakem se v datove casti kazdeho objektu na halde nachazi dany field

    public Attribute[] attrs; // attributes

    public boolean isReference() {
        return descriptor.startsWith("L");
    }

    public boolean isPrimitiveArray() {
        return descriptor.startsWith("[") && !descriptor.startsWith("[L");
    }

    public boolean isObjectArray() {
        return descriptor.startsWith("[L");
    }

}

package compactjvm.classfile;

import compactjvm.attributes.Attribute;

/**
 * Field info entity
 * @author Adam Vesecky
 */
public class FLEntity {

    public int accessFlags;
    public int nameIndex;
    public int descriptorIndex;
    public int attributesCount;
    public String name;
    public String descriptor;
    // index at which we can find it in the data part of the heap
    public int dataFieldOffset; 

    public Attribute[] attrs; 

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

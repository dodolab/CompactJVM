package cz.cvut.fit.compactjvm.attributes;

/**
 * Common attribute; used in methodinfo, fieldinfo and separately
 * @author Adam Vesecky
 */
public class Attribute {
    // index into constant pool, must point to UTF8 info
    public int nameIndex;
    // attribute lenth
    public int length;
    // attribute name
    public String name;
}

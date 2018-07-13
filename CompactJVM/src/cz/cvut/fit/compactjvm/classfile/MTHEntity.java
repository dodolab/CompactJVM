package cz.cvut.fit.compactjvm.classfile;

import cz.cvut.fit.compactjvm.attributes.AttrCode;
import cz.cvut.fit.compactjvm.attributes.Attribute;

/**
 *
 * @author Adam Vesecky
 */
public class MTHEntity {

    public int accessFlags;
    public int nameIndex;
    public int descriptorIndex;
    public int attributesCount;
    public String name;
    public String descriptor;

    public Attribute[] attrs;

    public AttrCode getCodeAttribute() {
        for (int i = 0; i < attributesCount; ++i) {
            if (attrs[i] instanceof AttrCode) {
                return (AttrCode) attrs[i];
            }
        }
        //throw new Exception("No Code attribute in method >>"+nameIndex+"<<"); 
        return null;
        //@todo nejaka exception poradna
    }

    public boolean isNativeMethod() {
        return (this.accessFlags & 0x0100) == 0x0100;
    }
}

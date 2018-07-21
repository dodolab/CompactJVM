package cz.cvut.fit.compactjvm.classfile;

import cz.cvut.fit.compactjvm.attributes.AttrCode;
import cz.cvut.fit.compactjvm.attributes.Attribute;
import cz.cvut.fit.compactjvm.definitions.MethodAccessFlag;

/**
 * Method info entity
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
        return null;
    }

    public boolean isNativeMethod() {
         return (this.accessFlags & MethodAccessFlag.ACC_NATIVE) == MethodAccessFlag.ACC_NATIVE;
    }
}

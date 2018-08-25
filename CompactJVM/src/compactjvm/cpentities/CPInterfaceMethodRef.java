package compactjvm.cpentities;

import compactjvm.definitions.ConstantPoolType;

/**
 * Constant pool interface method entity
 * @author Adam Vesecky
 */
public class CPInterfaceMethodRef extends CPEntity{
 
    public int classIndex; // item must be an interface type
    public int nameAndTypeIndex;
    
    public CPInterfaceMethodRef(){
        super(ConstantPoolType.CPT_InterfaceMethodref);
    }
    
    @Override
    public int getSize() {
        return 1;
    }
}

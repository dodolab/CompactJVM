package compactjvm.cpentities;

import compactjvm.definitions.ConstantPoolType;

/**
 * Constant pool dynamic invoking entity
 * @author Adam Vesecky
 */
public class CPInvokeDynamic extends CPEntity {
    
    public int bootstrapMethodAttrIndex;
    public int nameAndTypeIndex;
    
    public CPInvokeDynamic(){
        super(ConstantPoolType.CPT_InvokeDynamic);
    }
    
    @Override
    public int getSize() {
        return 1;
    }
}

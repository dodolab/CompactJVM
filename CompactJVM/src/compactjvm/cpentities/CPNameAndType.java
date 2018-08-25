package compactjvm.cpentities;

import compactjvm.definitions.ConstantPoolType;

/**
 * Constant pool name and type entity
 * @author Adam Vesecky
 */
public class CPNameAndType extends CPEntity {
 
    public int nameIndex;
    public int descriptorIndex; // must be valid index into the constant_pool table
    
    public CPNameAndType(){
        super(ConstantPoolType.CPT_NameAndType);
    }
    
    @Override
    public int getSize() {
        return 1;
    }
}

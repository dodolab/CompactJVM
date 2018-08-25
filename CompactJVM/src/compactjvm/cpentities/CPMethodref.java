package compactjvm.cpentities;

import compactjvm.definitions.ConstantPoolType;

/**
 * Constant pool method reference entity
 * @author Adam Vesecky
 */
public class CPMethodref extends CPEntity{
 
    public int classIndex; // item must be a class type
    
    // if begins with <(\u003c, then name must be special name <init>, representing
    // an instance initialization method; return type of such a method must be void
    public int nameAndTypeIndex;
    
    public CPMethodref(){
        super(ConstantPoolType.CPT_Methodref);
    }
    
    @Override
    public int getSize() {
        return 1;
    }
}

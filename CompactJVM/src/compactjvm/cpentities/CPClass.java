package compactjvm.cpentities;

import compactjvm.definitions.ConstantPoolType;

/**
 * Constant pool class entity
 * @author Adam Vesecky
 */
public class CPClass extends CPEntity{
   
    public int nameIndex;
     
    public CPClass(){
        super(ConstantPoolType.CPT_Class);
    }

    @Override
    public int getSize() {
        return 1;
    }
 
}

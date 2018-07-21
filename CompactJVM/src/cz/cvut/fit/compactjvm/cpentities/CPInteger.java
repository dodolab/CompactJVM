package cz.cvut.fit.compactjvm.cpentities;

import cz.cvut.fit.compactjvm.definitions.ConstantPoolType;

/**
 * Constant pool integer entity
 * @author Adam Vesecky
 */
public class CPInteger extends CPEntity{
 
    public int intVal;
    
    public CPInteger(){
        super(ConstantPoolType.CPT_Integer);
    }
    
    @Override
    public int getSize() {
        return 1;
    }
}

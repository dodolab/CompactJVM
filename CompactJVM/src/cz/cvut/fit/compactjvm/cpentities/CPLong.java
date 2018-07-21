package cz.cvut.fit.compactjvm.cpentities;

import cz.cvut.fit.compactjvm.definitions.ConstantPoolType;


/**
 * Constant pool long entity
 * @author Adam Vesecky
 */
public class CPLong extends CPEntity{
    
    public long longVal;
    
    public CPLong(){
        super(ConstantPoolType.CPT_Long);
    }
   
    @Override
    public int getSize() {
        return 2;
    }
}

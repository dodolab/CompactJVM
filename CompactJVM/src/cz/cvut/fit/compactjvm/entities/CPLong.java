package cz.cvut.fit.compactjvm.entities;

import cz.cvut.fit.compactjvm.definitions.ConstantPoolType;
import java.io.DataInputStream;

/**
 *
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

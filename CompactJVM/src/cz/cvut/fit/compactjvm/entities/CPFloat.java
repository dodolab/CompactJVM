package cz.cvut.fit.compactjvm.entities;

import cz.cvut.fit.compactjvm.definitions.ConstantPoolType;
import java.io.DataInputStream;

/**
 *
 * @author Adam Vesecky
 */
public class CPFloat extends CPEntity{
    
    public float floatVal;
    
    public CPFloat(){
        super(ConstantPoolType.CPT_Float);
    }
    
    @Override
    public int getSize() {
        return 1;
    }
}

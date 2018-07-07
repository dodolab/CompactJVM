package cz.cvut.fit.compactjvm.entities;

import cz.cvut.fit.compactjvm.definitions.ConstantPoolType;
import java.io.DataInputStream;

/**
 *
 * @author Adam Vesecky
 */
public class CPDouble extends CPEntity{
    
    public double doubleVal;
    
    public CPDouble(){
        super(ConstantPoolType.CPT_Double);
    }
    
    @Override
    public int getSize() {
        return 2;
    }
}

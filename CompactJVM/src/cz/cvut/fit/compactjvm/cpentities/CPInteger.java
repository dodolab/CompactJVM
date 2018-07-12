package cz.cvut.fit.compactjvm.cpentities;

import cz.cvut.fit.compactjvm.definitions.ConstantPoolType;
import java.io.DataInputStream;

/**
 *
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

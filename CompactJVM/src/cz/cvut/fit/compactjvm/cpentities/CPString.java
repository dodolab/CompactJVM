package cz.cvut.fit.compactjvm.cpentities;

import cz.cvut.fit.compactjvm.definitions.ConstantPoolType;
import java.io.DataInputStream;

/**
 *
 * @author Adam Vesecky
 */
public class CPString extends CPEntity {
 
    public int stringIndex;
    
    public CPString(){
        super(ConstantPoolType.CPT_String);
    }
    
    @Override
    public int getSize() {
        return 1;
    }
}

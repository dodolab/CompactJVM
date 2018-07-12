package cz.cvut.fit.compactjvm.cpentities;

import cz.cvut.fit.compactjvm.definitions.ConstantPoolType;
import java.io.DataInputStream;

/**
 *
 * @author Adam Vesecky
 */
public class CPMethodHandle extends CPEntity {
 
    public int referenceKind; // must be in the range 1-9
    public int referenceIndex;
    
    public CPMethodHandle(){
        super(ConstantPoolType.CPT_MethodHandle);
    }

    @Override
    public int getSize() {
        return 1;
    }
}

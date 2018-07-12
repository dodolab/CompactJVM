package cz.cvut.fit.compactjvm.cpentities;

import cz.cvut.fit.compactjvm.definitions.ConstantPoolType;
import java.io.DataInputStream;

/**
 *
 * @author Adam Vesecky
 */
public class CPMethodType extends CPEntity {
 
    public int descriptorIndex;
    
    public CPMethodType(){
        super(ConstantPoolType.CPT_MethodType);
    }

    @Override
    public int getSize() {
        return 1;
    }
}

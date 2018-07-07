package cz.cvut.fit.compactjvm.entities;

import cz.cvut.fit.compactjvm.definitions.ConstantPoolType;
import java.io.DataInputStream;

/**
 *
 * @author Adam Vesecky
 */
public class CPInvokeDynamic extends CPEntity {
    
    public int bootstrapMethodAttrIndex;
    public int nameAndTypeIndex;
    
    public CPInvokeDynamic(){
        super(ConstantPoolType.CPT_InvokeDynamic);
    }
    
    @Override
    public int getSize() {
        return 1;
    }
}

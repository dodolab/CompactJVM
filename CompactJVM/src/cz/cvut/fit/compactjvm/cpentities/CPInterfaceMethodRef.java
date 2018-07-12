package cz.cvut.fit.compactjvm.cpentities;

import cz.cvut.fit.compactjvm.definitions.ConstantPoolType;
import java.io.DataInputStream;

/**
 *
 * @author Adam Vesecky
 */
public class CPInterfaceMethodRef extends CPEntity{
 
    public int classIndex; // item must be an interface type
    public int nameAndTypeIndex;
    
    public CPInterfaceMethodRef(){
        super(ConstantPoolType.CPT_InterfaceMethodref);
    }
    
    @Override
    public int getSize() {
        return 1;
    }
}

package cz.cvut.fit.compactjvm.cpentities;

import cz.cvut.fit.compactjvm.definitions.ConstantPoolType;
import java.io.DataInputStream;

/**
 *
 * @author Adam Vesecky
 */
public class CPFieldRef extends CPEntity {
 
    public int classIndex; // item may be either a class type or an interface type
    public int nameAndTypeIndex;
    
    public CPFieldRef(){
        super(ConstantPoolType.CPT_Fieldref);
    }

    @Override
    public int getSize() {
        return 1;
    }
}

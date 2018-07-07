package cz.cvut.fit.compactjvm.entities;

import cz.cvut.fit.compactjvm.definitions.ConstantPoolType;
import java.io.DataInputStream;

/**
 *
 * @author Adam Vesecky
 */
public class CPClass extends CPEntity{
   
    public int nameIndex;
     
    public CPClass(){
        super(ConstantPoolType.CPT_Class);
    }

    @Override
    public int getSize() {
        return 1;
    }
 
}

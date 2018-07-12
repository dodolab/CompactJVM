package cz.cvut.fit.compactjvm.cpentities;

import cz.cvut.fit.compactjvm.definitions.ConstantPoolType;
import java.io.DataInputStream;

/**
 *
 * @author Adam Vesecky
 */
public class CPUtf8 extends CPEntity{
 
    public int length;
    // bytes of the string
    // no byte may have the value 0 or lie in the range 0xf0 - (byte)0xff
    public byte[] bytes; 
    // here will be value transformed from the byte array above
    public String value;
    
    public CPUtf8(){
        super(ConstantPoolType.CPT_Utf8);
    }
    
    @Override
    public int getSize() {
        return 1;
    }
}

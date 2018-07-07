package cz.cvut.fit.compactjvm.parsing;

import cz.cvut.fit.compactjvm.entities.EntAttribute;
import cz.cvut.fit.compactjvm.entities.MTHEntity;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Parser for methodinfo
 * @author Adam Vesecky
 */
public class MethodInfoParser {
    
    public MTHEntity parseMethodEntity(DataInputStream dis) throws IOException{
        MTHEntity ent = new MTHEntity();
        ent.accessFlags = dis.readShort();
        ent.nameIndex = dis.readShort();
        ent.descriptorIndex = dis.readShort();
        ent.attributesCount = dis.readShort();
        ent.attrs = new EntAttribute[ent.attributesCount];
        
        for(int i=0; i<ent.attributesCount; i++){
            EntAttribute attr = new EntAttribute();
            attr.nameIndex = dis.readShort();
            ent.attrs[i] = attr;
        }

        return ent;
    }
    
}

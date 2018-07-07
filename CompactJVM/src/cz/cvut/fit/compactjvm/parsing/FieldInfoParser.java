package cz.cvut.fit.compactjvm.parsing;

import cz.cvut.fit.compactjvm.entities.EntAttribute;
import cz.cvut.fit.compactjvm.entities.FLEntity;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Parser for fieldinfo
 * @author Adam Vesecky
 */
public class FieldInfoParser {

    public FLEntity parseFieldEntity(DataInputStream dis) throws IOException{
        FLEntity ent = new FLEntity();
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
package cz.cvut.fit.compactjvm.classloader.parsing;

import cz.cvut.fit.compactjvm.core.ClassFile;
import cz.cvut.fit.compactjvm.entities.CPUtf8;
import cz.cvut.fit.compactjvm.entities.Attribute;
import cz.cvut.fit.compactjvm.entities.FLEntity;
import cz.cvut.fit.compactjvm.exceptions.ParsingException;
import cz.cvut.fit.compactjvm.logging.JVMLogger;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Parser for fieldinfo
 *
 * @author Adam Vesecky
 */
public class FieldInfoParser {

    public int nextFieldOffset = 0;
    
    public FLEntity parseFieldEntity(ClassFile cls, DataInputStream dis) throws IOException, ParsingException {
        FLEntity ent = new FLEntity();
        ent.accessFlags = dis.readShort();
        ent.nameIndex = dis.readShort();
        ent.descriptorIndex = dis.readShort();
        ent.attributesCount = dis.readShort();

        // nameindex and descriptorindex must point into constant pool UTF8 entity
        String name = ((CPUtf8) cls.cpEntities[ent.nameIndex]).value;
        String descriptor = ((CPUtf8) cls.cpEntities[ent.descriptorIndex]).value;

        ent.name = name;
        ent.descriptor = descriptor;
        
        ent.dataFieldOffset = getFieldOffset(descriptor);
        
        JVMLogger.log(JVMLogger.TAG_PARSING, "Parsed field entity; access flags: " + ent.accessFlags
                + " ;name: " + name + " ;descriptor:" + descriptor
                + " ;attributesCount: " + ent.attributesCount,4);
        
        if (ent.attributesCount != 0) {
            JVMLogger.log(JVMLogger.TAG_PARSING, "Parsing attributes",4);
            
            ent.attrs = new Attribute[ent.attributesCount];

            AttributeParser parser = new AttributeParser();
            
            for (int i = 0; i < ent.attributesCount; i++) {
                Attribute attr = parser.parseAttributeEntity(cls,dis);
                ent.attrs[i] = attr;
            }
        }
        return ent;
    }

    //Spocita offset v datove casti kazdeho objektu na heape
    private int getFieldOffset(String descriptor) {
        int currentFieldOffset = nextFieldOffset;
        nextFieldOffset += 1;//("J".equals(descriptor) || "D".equals(descriptor)) ? 2 : 1;
        return currentFieldOffset;
    }

    public int getRecursiveFieldCount() {
        return nextFieldOffset;
    }
    
}

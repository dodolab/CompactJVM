package cz.cvut.fit.compactjvm.parsing;

import cz.cvut.fit.compactjvm.attributes.AttrCode;
import cz.cvut.fit.compactjvm.attributes.Attribute;
import cz.cvut.fit.compactjvm.attributes.AttrSourceFile;
import cz.cvut.fit.compactjvm.attributes.AttrExcTableItem;
import cz.cvut.fit.compactjvm.attributes.AttrConstantVal;
import cz.cvut.fit.compactjvm.cpentities.CPUtf8;
import cz.cvut.fit.compactjvm.classfile.ClassFile;
import cz.cvut.fit.compactjvm.definitions.AttributeType;
import cz.cvut.fit.compactjvm.exceptions.ParsingException;
import java.io.DataInputStream;
import java.io.IOException;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;


/**
 * Attribute parser
 * @author Adam Vesecky
 */
public class AttributeParser {

    /**
     * Parses attribute from classfile
     * @throws ParsingException 
     */
    public Attribute parseAttributeEntity(ClassFile cls, DataInputStream dis) throws IOException, ParsingException {

        int nameIndex = dis.readShort();
        // nameindex must point into constant pool UTF8 entity
        String name = ((CPUtf8) cls.cpEntities[nameIndex]).value;

        Attribute attr = null;

        JVMLogger.log(JVMLogger.TAG_PARSING, "Parsing attribute "+name,10);
        
        // parse only attribute that we may need ...
        
        switch (name) {
            case AttributeType.ATTR_ANNOTDEF:
                skipAttribute(cls, dis);
                break;
            case AttributeType.ATTR_BOOTSTRAP:
                skipAttribute(cls, dis);
                break;
            case AttributeType.ATTR_CODE:
                attr = parseCodeAttribute(cls, dis);
                break;
            case AttributeType.ATTR_CONSTANTVALUE:
                attr = parseConstantVal(cls, dis);
                break;
            case AttributeType.ATTR_DEPRECATED:
                skipAttribute(cls, dis);
                break;
            case AttributeType.ATTR_ENCLOSINGMETHOD:
                skipAttribute(cls, dis);
                break;
            case AttributeType.ATTR_EXCEPTIONS:
                skipAttribute(cls, dis);
                break;
            case AttributeType.ATTR_INNERCLASSES:
                skipAttribute(cls, dis);
                break;
            case AttributeType.ATTR_LINENUMBERTABLE:
                skipAttribute(cls, dis);
                break;
            case AttributeType.ATTR_LOCALVARTAB:
                skipAttribute(cls, dis);
                break;
            case AttributeType.ATTR_LOCALVARTYPETAB:
                skipAttribute(cls, dis);
                break;
            case AttributeType.ATTR_RUNTIMEINVISANNOT:
                skipAttribute(cls, dis);
                break;
            case AttributeType.ATTR_RUNTIMEINVISPARANNOT:
                skipAttribute(cls, dis);
                break;
            case AttributeType.ATTR_RUNTIMEVISANNOT:
                skipAttribute(cls, dis);
                break;
            case AttributeType.ATTR_RUNTIMEVISPARANNOT:
                skipAttribute(cls, dis);
                break;
            case AttributeType.ATTR_SIGNATURE:
                skipAttribute(cls, dis);
                break;
            case AttributeType.ATTR_SOURCEDEBUGEXT:
                skipAttribute(cls, dis);
                break;
            case AttributeType.ATTR_SOURCEFILE:
                attr = parseSourceFile(cls, dis);
                break;
            case AttributeType.ATTR_STACKMAPTAB:
                skipAttribute(cls, dis);
                break;
            case AttributeType.ATTR_SYNTHETIC:
                skipAttribute(cls, dis);
                break;
            default:
                throw new ParsingException("Unknown attribute!");
        }

        // set name index at last
        if(attr != null){
            attr.nameIndex = nameIndex;
            attr.name = name;
        }
        return attr;
    }

    private void skipAttribute(ClassFile cls, DataInputStream dis) throws IOException{
        JVMLogger.log(JVMLogger.TAG_PARSING, "Not supported ---> skipping",14);
        int length = dis.readInt();
        byte[] arrToSkip = new byte[length];
        dis.read(arrToSkip);
    }
    
    private AttrCode parseCodeAttribute(ClassFile cls, DataInputStream dis) throws IOException, ParsingException {
        AttrCode attr = new AttrCode();
        // note: there is indeed an integer
                attr.length = dis.readInt();
        attr.maxStack = dis.readShort();
        attr.maxLocals = dis.readShort();
        attr.codeLength = dis.readInt();
        attr.code = new byte[attr.codeLength];
        // load bytes
        dis.read(attr.code);
        attr.exceptionTableLength = dis.readShort();

        JVMLogger.log(JVMLogger.TAG_PARSING, "MaxStack: "+attr.maxStack+"; MaxLocals: "+attr.maxLocals+
                "; CodeLength: "+attr.codeLength,10);
        
        // parse exception table
        if (attr.exceptionTableLength != 0) {
            attr.exceptionTable = new AttrExcTableItem[attr.exceptionTableLength];

            for (int i = 0; i < attr.exceptionTableLength; i++) {
                AttrExcTableItem item = new AttrExcTableItem();
                item.startPc = dis.readShort();
                item.endPc = dis.readShort();
                item.handlerPc = dis.readShort();
                item.catchType = dis.readShort();

                attr.exceptionTable[i] = item;
            }
        }

        // parse inner attributes
        attr.attributesCount = dis.readShort();

        if (attr.attributesCount != 0) {
            JVMLogger.log(JVMLogger.TAG_PARSING, "Parsing inner attributes",10);
            attr.attrs = new Attribute[attr.attributesCount];

            for (int i = 0; i < attr.attributesCount; i++) {
                Attribute innerAttr = this.parseAttributeEntity(cls, dis);
                attr.attrs[i] = innerAttr;
            }
        }

        return attr;
    }

    private AttrConstantVal parseConstantVal(ClassFile cls, DataInputStream dis) throws IOException {
        AttrConstantVal attr = new AttrConstantVal();
        
        attr.length = dis.readInt();
        attr.constantValIndex = dis.readShort();
        
        if(cls.cpEntities[attr.constantValIndex] instanceof CPUtf8) {
            String constantVal = ((CPUtf8)cls.cpEntities[attr.constantValIndex]).value;
            JVMLogger.log(JVMLogger.TAG_PARSING, "Constant value: "+constantVal,10);
        }
        return attr;
    }

    private AttrSourceFile parseSourceFile(ClassFile cls, DataInputStream dis) throws IOException {
        AttrSourceFile attr = new AttrSourceFile();
        
        attr.length = dis.readInt();
        attr.sourceFileIndex = dis.readShort();
        
        String sourceFile = ((CPUtf8)cls.cpEntities[attr.sourceFileIndex]).value;
        JVMLogger.log(JVMLogger.TAG_PARSING, "Source file: "+sourceFile,10);
        return attr;
    }
}

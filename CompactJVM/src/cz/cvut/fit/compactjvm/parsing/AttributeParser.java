package cz.cvut.fit.compactjvm.parsing;

import cz.cvut.fit.compactjvm.core.ClassFile;
import cz.cvut.fit.compactjvm.definitions.AttributeType;
import cz.cvut.fit.compactjvm.entities.*;
import cz.cvut.fit.compactjvm.exceptions.ParsingException;
import java.io.DataInputStream;
import java.io.IOException;

/**
 *
 * @author Adam Vesecky
 */
public class AttributeParser {

    public Attribute parseAttributeEntity(ClassFile cls, DataInputStream dis) throws IOException, ParsingException {

        int nameIndex = dis.readShort();
        // nameindex must point into constant pool UTF8 entity
        String name = ((CPUtf8) cls.cpEntities[nameIndex]).value;

        Attribute attr = null;

        System.out.println("          Parsing attribute "+name);
        
        switch (name) {
            case AttributeType.ATTR_ANNOTDEF:
                skipAttribute(cls, dis);
                break;
            case AttributeType.ATTR_BOOTSTRAP:
                skipAttribute(cls, dis);
                break;
            case AttributeType.ATTR_CODE://
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
        if(attr != null) attr.nameIndex = nameIndex;
        return attr;
    }

    private void skipAttribute(ClassFile cls, DataInputStream dis) throws IOException{
        System.out.print("              Not supported ---> skipping");
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

        System.out.println("          MaxStack: "+attr.maxStack+"; MaxLocals: "+attr.maxLocals+
                "; CodeLength: "+attr.codeLength);
        
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
            System.out.println("          Parsing inner attributes");
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
        
        String constantVal = ((CPUtf8)cls.cpEntities[attr.constantValIndex]).value;
        System.out.println("          Constant value: "+constantVal);
        return attr;
    }

    private AttrSourceFile parseSourceFile(ClassFile cls, DataInputStream dis) throws IOException {
        AttrSourceFile attr = new AttrSourceFile();
        
        attr.length = dis.readInt();
        attr.sourceFileIndex = dis.readShort();
        
        String sourceFile = ((CPUtf8)cls.cpEntities[attr.sourceFileIndex]).value;
        System.out.println("          Source file: "+sourceFile);
        return attr;
    }

}

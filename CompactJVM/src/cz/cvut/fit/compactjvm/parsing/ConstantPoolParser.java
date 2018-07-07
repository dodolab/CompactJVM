package cz.cvut.fit.compactjvm.parsing;

import cz.cvut.fit.compactjvm.definitions.ConstantPoolType;
import cz.cvut.fit.compactjvm.entities.*;
import cz.cvut.fit.compactjvm.exceptions.ParsingException;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Parser for constant pool
 * @author Adam Vesecky
 */
public class ConstantPoolParser {
    
    public CPEntity parseConstantPoolEntity(DataInputStream dis) throws IOException{
        short cstType = (short) dis.readUnsignedByte();
        CPEntity ent = null;
        
        // parse entity according to its type
        switch (cstType) {
                case ConstantPoolType.CPT_Class:
                    ent = this.parseCPClass(dis);
                    break;
                case ConstantPoolType.CPT_Double:
                    ent = this.parseCPDouble(dis);
                    break;
                case ConstantPoolType.CPT_Fieldref:
                    ent = this.parseCPFieldRef(dis);
                    break;
                case ConstantPoolType.CPT_Float:
                    ent = this.parseCPFloat(dis);
                    break;
                case ConstantPoolType.CPT_Integer:
                    ent = this.parseCPInteger(dis);
                    break;
                case ConstantPoolType.CPT_InterfaceMethodref:
                    ent = this.parseCPInterfaceMethodRef(dis);
                    break;
                case ConstantPoolType.CPT_InvokeDynamic:
                    ent = this.parseCPInvokeDynamic(dis);
                    break;
                case ConstantPoolType.CPT_Long:
                    ent = this.parseCPLong(dis);
                    break;
                case ConstantPoolType.CPT_MethodHandle:
                    ent = this.parseCPMethodHandle(dis);
                    break;
                case ConstantPoolType.CPT_MethodType:
                    ent = this.parseCPMethodType(dis);
                    break;    
                case ConstantPoolType.CPT_Methodref:
                    ent = this.parseCPMethodref(dis);
                    break;
                case ConstantPoolType.CPT_NameAndType:
                    ent = this.parseCPNameAndType(dis);
                    break;
                case ConstantPoolType.CPT_String:
                    ent = this.parseCPString(dis);
                    break;
                case ConstantPoolType.CPT_Utf8:
                    ent = this.parseCPUtf8(dis);
                    break;
                default:
                    System.out.println("Unknown constant pool type!");
            }
        
        return ent;
    }
    
    public CPClass parseCPClass(DataInputStream dis) throws IOException{
        System.out.println("Parsing CPClass");
        CPClass ent = new CPClass();
        ent.nameIndex = dis.readUnsignedShort();
        return ent;
    }
    
    public CPDouble parseCPDouble(DataInputStream dis) throws IOException{
        System.out.println("Parsing CPDouble");
        CPDouble ent = new CPDouble();
        ent.doubleVal = dis.readDouble();
        return ent;
    }
    
    public CPFieldRef parseCPFieldRef(DataInputStream dis) throws IOException{
        System.out.println("Parsing CPFieldRef");
        CPFieldRef ent = new CPFieldRef();
        ent.classIndex = dis.readUnsignedShort();
        ent.nameAndTypeIndex = dis.readUnsignedShort();
        return ent;
    }
    
    public CPFloat parseCPFloat(DataInputStream dis) throws IOException{
        System.out.println("Parsing CPFloat");
        CPFloat ent = new CPFloat();
        ent.floatVal = dis.readFloat();
        return ent;
    }
    
    public CPInteger parseCPInteger(DataInputStream dis) throws IOException{
        System.out.println("Parsing CPInteger");
        CPInteger ent = new CPInteger();
        ent.intVal = dis.readInt();
        return ent;
    }
    
    public CPInterfaceMethodRef parseCPInterfaceMethodRef(DataInputStream dis) throws IOException{
        System.out.println("Parsing CPInterfaceMethodRef");
        CPInterfaceMethodRef ent = new CPInterfaceMethodRef();
        ent.classIndex = dis.readUnsignedShort();
        ent.nameAndTypeIndex = dis.readUnsignedShort();
        return ent;
    }
    
    public CPInvokeDynamic parseCPInvokeDynamic(DataInputStream dis) throws IOException{
        System.out.println("Parsing CPInvokeDynamic");
        CPInvokeDynamic ent = new CPInvokeDynamic();
        short c = dis.readShort();
	ent.bootstrapMethodAttrIndex = dis.readShort();
        ent.nameAndTypeIndex = dis.readShort();
        return ent;
    }
    
    public CPLong parseCPLong(DataInputStream dis) throws IOException{
        System.out.println("Parsing CPLong");
        CPLong ent = new CPLong();
        ent.longVal = dis.readLong();
        return ent;
    }
    
    public CPMethodHandle parseCPMethodHandle(DataInputStream dis) throws IOException{
        System.out.println("Parsing CPMethodHandle");
        CPMethodHandle ent = new CPMethodHandle();
        ent.referenceKind = dis.readShort();
        ent.referenceIndex = dis.readShort();
        
        return ent;
    }
    
    public CPMethodType parseCPMethodType(DataInputStream dis) throws IOException{
        System.out.println("Parsing CPMethodType");
        CPMethodType ent = new CPMethodType();
        ent.descriptorIndex = dis.readShort();
        return ent;
    }
    
    public CPMethodref parseCPMethodref(DataInputStream dis) throws IOException{
        System.out.println("Parsing CPMethodref");
        CPMethodref ent = new CPMethodref();
        ent.classIndex = dis.readShort();
        ent.nameAndTypeIndex = dis.readShort();
        return ent;
    }
    
    public CPNameAndType parseCPNameAndType(DataInputStream dis) throws IOException{
        System.out.println("Parsing CPNameAndType");
        CPNameAndType ent = new CPNameAndType();
        ent.nameIndex = dis.readUnsignedShort();
        ent.descriptorIndex = dis.readUnsignedShort();
        return ent;
    }
    
    public CPString parseCPString(DataInputStream dis) throws IOException{
        System.out.println("Parsing CPString");
        CPString ent = new CPString();
        ent.stringIndex = dis.readUnsignedShort();
        return ent;
    }
    
    public CPUtf8 parseCPUtf8(DataInputStream dis) throws IOException{
        System.out.println("Parsing CPUtf8");
        CPUtf8 ent = new CPUtf8();
        // load byte arry
        ent.length = dis.readUnsignedShort();
        ent.bytes = new byte[ent.length];
        // transform bytes to UTF8 string
        ent.value = new String(ent.bytes, "UTF-8");  
        dis.read(ent.bytes);
        return ent;
    }
}

package compactjvm.parsing;

import compactjvm.cpentities.CPString;
import compactjvm.cpentities.CPFloat;
import compactjvm.cpentities.CPInvokeDynamic;
import compactjvm.cpentities.CPNameAndType;
import compactjvm.cpentities.CPEntity;
import compactjvm.cpentities.CPMethodref;
import compactjvm.cpentities.CPInterfaceMethodRef;
import compactjvm.cpentities.CPFieldRef;
import compactjvm.cpentities.CPLong;
import compactjvm.cpentities.CPMethodHandle;
import compactjvm.cpentities.CPUtf8;
import compactjvm.cpentities.CPMethodType;
import compactjvm.cpentities.CPInteger;
import compactjvm.cpentities.CPDouble;
import compactjvm.cpentities.CPClass;
import compactjvm.definitions.ConstantPoolType;
import java.io.DataInputStream;
import java.io.IOException;
import compactjvm.jvm.JVMLogger;

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
                    JVMLogger.log(JVMLogger.TAG_PARSING, "Unknown constant pool type!");
            }
        
        return ent;
    }
    
    
    public CPClass parseCPClass(DataInputStream dis) throws IOException{
        JVMLogger.log(JVMLogger.TAG_PARSING, "Parsing CPClass",4);
        CPClass ent = new CPClass();
        ent.nameIndex = dis.readUnsignedShort();
        return ent;
    }
    
    public CPDouble parseCPDouble(DataInputStream dis) throws IOException{
        JVMLogger.log(JVMLogger.TAG_PARSING, "Parsing CPDouble",4);
        CPDouble ent = new CPDouble();
        ent.doubleVal = dis.readDouble();
        return ent;
    }
    
    public CPFieldRef parseCPFieldRef(DataInputStream dis) throws IOException{
        JVMLogger.log(JVMLogger.TAG_PARSING, "Parsing CPFieldRef",4);
        CPFieldRef ent = new CPFieldRef();
        ent.classIndex = dis.readUnsignedShort();
        ent.nameAndTypeIndex = dis.readUnsignedShort();
        return ent;
    }
    
    public CPFloat parseCPFloat(DataInputStream dis) throws IOException{
        JVMLogger.log(JVMLogger.TAG_PARSING, "Parsing CPFloat",4);
        CPFloat ent = new CPFloat();
        ent.floatVal = dis.readFloat();
        return ent;
    }
    
    public CPInteger parseCPInteger(DataInputStream dis) throws IOException{
        JVMLogger.log(JVMLogger.TAG_PARSING, "Parsing CPInteger",4);
        CPInteger ent = new CPInteger();
        ent.intVal = dis.readInt();
        return ent;
    }
    
    public CPInterfaceMethodRef parseCPInterfaceMethodRef(DataInputStream dis) throws IOException{
        JVMLogger.log(JVMLogger.TAG_PARSING, "Parsing CPInterfaceMethodRef",4);
        CPInterfaceMethodRef ent = new CPInterfaceMethodRef();
        ent.classIndex = dis.readUnsignedShort();
        ent.nameAndTypeIndex = dis.readUnsignedShort();
        return ent;
    }
    
    public CPInvokeDynamic parseCPInvokeDynamic(DataInputStream dis) throws IOException{
        JVMLogger.log(JVMLogger.TAG_PARSING, "Parsing CPInvokeDynamic",4);
        CPInvokeDynamic ent = new CPInvokeDynamic();
        short c = dis.readShort();
	ent.bootstrapMethodAttrIndex = dis.readShort();
        ent.nameAndTypeIndex = dis.readShort();
        return ent;
    }
    
    public CPLong parseCPLong(DataInputStream dis) throws IOException{
        JVMLogger.log(JVMLogger.TAG_PARSING, "Parsing CPLong",4);
        CPLong ent = new CPLong();
        ent.longVal = dis.readLong();
        return ent;
    }
    
    public CPMethodHandle parseCPMethodHandle(DataInputStream dis) throws IOException{
        JVMLogger.log(JVMLogger.TAG_PARSING, "Parsing CPMethodHandle",4);
        CPMethodHandle ent = new CPMethodHandle();
        ent.referenceKind = dis.readShort();
        ent.referenceIndex = dis.readShort();
        
        return ent;
    }
    
    public CPMethodType parseCPMethodType(DataInputStream dis) throws IOException{
        JVMLogger.log(JVMLogger.TAG_PARSING, "Parsing CPMethodType",4);
        CPMethodType ent = new CPMethodType();
        ent.descriptorIndex = dis.readShort();
        return ent;
    }
    
    public CPMethodref parseCPMethodref(DataInputStream dis) throws IOException{
        JVMLogger.log(JVMLogger.TAG_PARSING, "Parsing CPMethodref",4);
        CPMethodref ent = new CPMethodref();
        ent.classIndex = dis.readShort();
        ent.nameAndTypeIndex = dis.readShort();
        return ent;
    }
    
    public CPNameAndType parseCPNameAndType(DataInputStream dis) throws IOException{
        JVMLogger.log(JVMLogger.TAG_PARSING, "Parsing CPNameAndType",4);
        CPNameAndType ent = new CPNameAndType();
        ent.nameIndex = dis.readUnsignedShort();
        ent.descriptorIndex = dis.readUnsignedShort();
        return ent;
    }
    
    public CPString parseCPString(DataInputStream dis) throws IOException{
        JVMLogger.log(JVMLogger.TAG_PARSING, "Parsing CPString",4);
        CPString ent = new CPString();
        ent.stringIndex = dis.readUnsignedShort();
        return ent;
    }
    
    public CPUtf8 parseCPUtf8(DataInputStream dis) throws IOException{
        JVMLogger.log(JVMLogger.TAG_PARSING, "Parsing CPUtf8",4);
        CPUtf8 ent = new CPUtf8();
        // load byte arry
        ent.length = dis.readUnsignedShort();
        ent.bytes = new byte[ent.length];
        dis.read(ent.bytes);
        // transform bytes to UTF8 string
        ent.value = new String(ent.bytes, "UTF-8");  
        JVMLogger.log(JVMLogger.TAG_PARSING, "UTF8 value: "+ent.value,4);
        return ent;
    }
}

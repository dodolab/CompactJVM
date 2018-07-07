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
    
    public CPClass parseCPClass(DataInputStream dis){
        return null;
    }
    
    public CPDouble parseCPDouble(DataInputStream dis){
        return null;
    }
    
    public CPFieldRef parseCPFieldRef(DataInputStream dis){
        return null;
    }
    
    public CPFloat parseCPFloat(DataInputStream dis){
        return null;
    }
    
    public CPInteger parseCPInteger(DataInputStream dis){
        return null;
    }
    
    public CPInterfaceMethodRef parseCPInterfaceMethodRef(DataInputStream dis){
        return null;
    }
    
    public CPInvokeDynamic parseCPInvokeDynamic(DataInputStream dis){
        return null;
    }
    
    public CPLong parseCPLong(DataInputStream dis){
        return null;
    }
    
    public CPMethodHandle parseCPMethodHandle(DataInputStream dis){
        return null;
    }
    
    public CPMethodType parseCPMethodType(DataInputStream dis){
        return null;
    }
    
    public CPMethodref parseCPMethodref(DataInputStream dis){
        return null;
    }
    
    public CPNameAndType parseCPNameAndType(DataInputStream dis){
        return null;
    }
    
    public CPString parseCPString(DataInputStream dis){
        return null;
    }
    
    public CPUtf8 parseCPUtf8(DataInputStream dis){
        return null;
    }
}

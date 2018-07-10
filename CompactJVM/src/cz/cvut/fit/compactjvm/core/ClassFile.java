package cz.cvut.fit.compactjvm.core;

import cz.cvut.fit.compactjvm.definitions.ConstantPoolType;
import cz.cvut.fit.compactjvm.entities.CPEntity;
import cz.cvut.fit.compactjvm.entities.Attribute;
import cz.cvut.fit.compactjvm.entities.CPClass;
import cz.cvut.fit.compactjvm.entities.CPFieldRef;
import cz.cvut.fit.compactjvm.entities.CPMethodref;
import cz.cvut.fit.compactjvm.entities.CPNameAndType;
import cz.cvut.fit.compactjvm.entities.CPUtf8;
import cz.cvut.fit.compactjvm.entities.FLEntity;
import cz.cvut.fit.compactjvm.entities.MTHEntity;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;


/**
 *
 * @author Adam Vesecky
 */
public class ClassFile {
    // todo: generate getters and setters later
    public short majorVersion;
    public short minorVersion;
    public short cpSize; // constant pool size
    public CPEntity[] cpEntities; // constant pool entities
    public short accessFlags;
    public short thisClassIndex;
    public short superClassIndex;
    public short interfaceCount;
    public short interfaceIndex;
    public short fieldCount; // number of fields
    public FLEntity[] fieldInfos; // field info
    public short methodCount; // number of methods
    public MTHEntity[] methodInfos; // method info
    public short attributeCount; // number of attributes
    public Attribute[] attributeInfos; // attribute info
    // indices into constant pool, each index shoul point to ClassInfo
    public int[] interfIndices;
    
    public int[] classVariables;
    public int index; // index of parsed class
    
    /**
     * Ziska Entitu odpovidajici pozadovane metode
     * @param index
     * @return 
     */
    public MTHEntity getMethod(int index) throws LoadingException {
        if(index >= methodCount) {
            throw new LoadingException("Method not found");
        }
        return methodInfos[index];
    }
    
    public FLEntity getField(int index) throws LoadingException{
        if(index >= fieldCount){
            throw new LoadingException("Field not found");
        }
        return fieldInfos[index];
    }
    
    /**
     * Ziska nazev tridy z constant poolu
     * @return 
     */
    public String getClassName() {
        CPClass stringEntity = (CPClass) cpEntities[thisClassIndex];
        return ((CPUtf8) cpEntities[stringEntity.nameIndex]).value;
    }
    
    /**
     * Nacte z constant poolu informace o metode, ktera je reprezentovana zadanym
     * indexem. nacte nazev tridy, nazev metody a descriptor.
     * @param methodRefIndex
     * @return 
     */
    public MethodDefinition getMethodDefinition(int methodRefIndex) throws LoadingException {
        if(cpEntities[methodRefIndex].tag != ConstantPoolType.CPT_Methodref) throw new LoadingException("Wrong method index");
        CPMethodref methodRef = (CPMethodref) cpEntities[methodRefIndex];
        CPNameAndType nameAndType = (CPNameAndType) cpEntities[methodRef.nameAndTypeIndex];
        int classNameIndex = ((CPClass) cpEntities[methodRef.classIndex]).nameIndex;
        String methodClass = ((CPUtf8) cpEntities[classNameIndex]).value;
        String methodName = ((CPUtf8) cpEntities[nameAndType.nameIndex]).value;
        String methodDescriptor = ((CPUtf8) cpEntities[nameAndType.descriptorIndex]).value;
        
        MethodDefinition method = new MethodDefinition(methodClass, methodName, methodDescriptor);
        return method;
    }
    
    /**
     * Loads field definition from constant pool according to selected index
     * @param fieldRefIndex
     * @return 
     */
    public FieldDefinition getFieldDefinition(int fieldRefIndex) throws LoadingException{
        if(cpEntities[fieldRefIndex].tag != ConstantPoolType.CPT_Fieldref) throw new LoadingException("Wrong field index");
        
        CPFieldRef fieldRef = (CPFieldRef) cpEntities[fieldRefIndex];
        CPNameAndType nameAndType = (CPNameAndType) cpEntities[fieldRef.nameAndTypeIndex];
        int classNameIndex = ((CPClass) cpEntities[fieldRef.classIndex]).nameIndex;
        String fieldClass = ((CPUtf8) cpEntities[classNameIndex]).value;
        String fieldName = ((CPUtf8) cpEntities[nameAndType.nameIndex]).value;
        String fieldDescriptor = ((CPUtf8) cpEntities[nameAndType.descriptorIndex]).value;
        
        FieldDefinition fieldDef = new FieldDefinition(fieldClass, fieldName, fieldDescriptor);
        return fieldDef;
    }

    /**
     * Ziska index v methodIndex
     * @param methodName
     * @param methodDescriptor
     * @return 
     */
    public int getMethodIndex(String methodName, String methodDescriptor) {
        for(int i = 0; i < methodInfos.length; ++i) {
            if(((CPUtf8) cpEntities[methodInfos[i].nameIndex]).value.equals(methodName)
                && ((CPUtf8) cpEntities[methodInfos[i].descriptorIndex]).value.equals(methodDescriptor))
            return i;
        }
        return 0;
        //@todo throw exception
    }
    
    /**
     * Gets field index by selected name and descriptor
     * @param fieldName
     * @param fieldDescriptor
     * @return 
     */
    public int getFieldIndex(String fieldName, String fieldDescriptor) throws LoadingException{
        for(int i=0; i< fieldInfos.length; i++){
            if(((CPUtf8) cpEntities[fieldInfos[i].nameIndex]).value.equals(fieldName)
                && ((CPUtf8) cpEntities[fieldInfos[i].descriptorIndex]).value.equals(fieldDescriptor))
            return i;
        }
        throw new LoadingException("Field index not found");
    }
}

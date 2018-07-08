package cz.cvut.fit.compactjvm.core;

import cz.cvut.fit.compactjvm.definitions.ConstantPoolType;
import cz.cvut.fit.compactjvm.entities.CPEntity;
import cz.cvut.fit.compactjvm.entities.Attribute;
import cz.cvut.fit.compactjvm.entities.CPClass;
import cz.cvut.fit.compactjvm.entities.CPMethodref;
import cz.cvut.fit.compactjvm.entities.CPNameAndType;
import cz.cvut.fit.compactjvm.entities.CPUtf8;
import cz.cvut.fit.compactjvm.entities.FLEntity;
import cz.cvut.fit.compactjvm.entities.MTHEntity;


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
    
    /**
     * Ziska Entitu odpovidajici pozadovane metode
     * @param index
     * @return 
     */
    public MTHEntity getMethod(int index) {
        if(index >= methodCount) {
            return null;
            //@todo nebo vyhodit vyjimku?
        }
        return methodInfos[index];
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
    public MethodDefinition getMethodDefinition(int methodRefIndex) {
        if(cpEntities[methodRefIndex].tag != ConstantPoolType.CPT_Methodref) return null;//@todo Exception
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
}

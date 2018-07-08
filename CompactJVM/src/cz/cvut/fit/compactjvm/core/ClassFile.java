package cz.cvut.fit.compactjvm.core;

import cz.cvut.fit.compactjvm.entities.CPEntity;
import cz.cvut.fit.compactjvm.entities.Attribute;
import cz.cvut.fit.compactjvm.entities.CPClass;
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
}

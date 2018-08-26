package compactjvm.classfile;

import compactjvm.definitions.ConstantPoolType;
import compactjvm.attributes.AttrCode;
import compactjvm.attributes.AttrExcTableItem;
import compactjvm.cpentities.CPEntity;
import compactjvm.attributes.Attribute;
import compactjvm.cpentities.CPClass;
import compactjvm.cpentities.CPFieldRef;
import compactjvm.cpentities.CPMethodref;
import compactjvm.cpentities.CPNameAndType;
import compactjvm.cpentities.CPUtf8;
import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.JVMLogger;
import compactjvm.jvm.JVMStack;
import compactjvm.jvm.MethodArea;
import compactjvm.jvm.StackFrame;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class file with all its methods, entities and fields
 *
 * @author Adam Vesecky
 */
public class ClassFile {

    public short majorVersion;
    public short minorVersion;
    public short cpSize; // constant pool size
    public CPEntity[] cpEntities; // constant pool entities
    public short accessFlags; // access flags
    public short thisClassIndex;
    public short superClassIndex;
    public short interfaceCount;
    public short interfaceIndex;
    public short fieldCount; // number of fields
    public int recursiveFieldCount; // number of fields (recursive counted with superclass fields)
    public FLEntity[] fieldInfos; // field info
    public Map<Integer, FLEntity> fieldInfosByCpIndex = null;
    public short methodCount; // number of methods
    public MTHEntity[] methodInfos; // method info
    public short attributeCount; // number of attributes
    public Attribute[] attributeInfos; // attribute info
    // indices into constant pool, each index shoul point to ClassInfo
    public int[] interfIndices;

    public int[] classVariables;
    public int index; // index of parsed class

    // during the reinitialization, it is necessary to recalculate offset of the field
    // in order to store it inside a heap according to the ancestors of the class
    public boolean fieldOffsetsRecalculated = false;
    public ClassFile superClass = null;
    public String className;

    /**
     * Gets name of the class from the constant pool
     */
    public String getClassName() {
        CPClass stringEntity = (CPClass) cpEntities[thisClassIndex];
        return ((CPUtf8) cpEntities[stringEntity.nameIndex]).value;
    }

    public void setClassName() {
        this.className = getClassName();
    }

    /**
     * Gets name of superclass
     *
     */
    public String getSuperclassName() {
        if (cpEntities[superClassIndex] == null) {
            return null;
        }
        CPClass cpClass = (CPClass) cpEntities[superClassIndex];
        return ((CPUtf8) cpEntities[cpClass.nameIndex]).value;
    }

    public ClassFile getSuperClass() {
        return superClass;
    }

    /**
     * Gets an entity corresponding to a given method
     *
     */
    public MTHEntity getMethod(int index) throws LoadingException {
        if (index >= methodCount) {
            throw new LoadingException("Method not found");
        }
        return methodInfos[index];
    }

    /**
     * Gets method by its name
     */
    public MTHEntity getMethod(String name) throws LoadingException {
        for (int i = 0; i < methodInfos.length; i++) {
            if (methodInfos[i].name.equals(name)) {
                return methodInfos[i];
            }
        }

        if (this.getSuperclassName() != null) {
            return this.getSuperClass().getMethod(name);
        }

        throw new LoadingException("Method " + name + " not found in class " + this.className);
    }

    /**
     * Gets field by index
     */
    public FLEntity getField(int index) throws LoadingException {
        if (index >= fieldCount) {
            throw new LoadingException("Field not found");
        }
        return fieldInfos[index];
    }

    /**
     * Loads info about a given method from the constant pool, represented
     * by a given index. Loads a name of the class, name of its method and a descriptor
     *
     */
    public MethodDefinition getMethodDefinition(int methodRefIndex, int methodDefIndex, MethodArea methodArea) throws LoadingException, IOException {
        if (cpEntities[methodRefIndex].tag != ConstantPoolType.CPT_Methodref) {
            throw new LoadingException("Wrong method index");
        }
        CPMethodref methodRef = (CPMethodref) cpEntities[methodRefIndex];
        CPNameAndType nameAndType = (CPNameAndType) cpEntities[methodRef.nameAndTypeIndex];
        int classNameIndex = ((CPClass) cpEntities[methodRef.classIndex]).nameIndex;
        String methodClass = ((CPUtf8) cpEntities[classNameIndex]).value;
        String methodName = ((CPUtf8) cpEntities[nameAndType.nameIndex]).value;
        String methodDescriptor = ((CPUtf8) cpEntities[nameAndType.descriptorIndex]).value;

        int accessFlags = 0;
        // get access flags from method info (important for identifying native methods)
        ClassFile methodCls;
        if (!this.className.equals(methodClass)) {
            methodCls = methodArea.getClassFile(methodClass);
        } else {
            methodCls = this;
        }

        MTHEntity methodEntity = methodCls.getMethod(methodName);
        accessFlags = methodEntity.accessFlags;

        MethodDefinition method = new MethodDefinition(methodClass, methodName, methodDescriptor, accessFlags);
        loadExceptionTable(methodEntity, method, methodDefIndex, methodArea);

        return method;
    }

    // used when we know descriptor and class name, there is no need to search constant pool
    public MethodDefinition getMethodDefinition(int methodDefIndex, MethodArea methodArea,
            String methodClass, String methodName, String methodDescriptor) throws LoadingException, IOException {

        int accessFlags = 0;
        // get access flags from method info (important for identifying native methods)
        ClassFile methodCls;
        if (!this.className.equals(methodClass)) {
            methodCls = methodArea.getClassFile(methodClass);
        } else {
            methodCls = this;
        }

        MTHEntity methodEntity = methodCls.getMethod(methodName);
        accessFlags = methodEntity.accessFlags;

        MethodDefinition method = new MethodDefinition(methodClass, methodName, methodDescriptor, accessFlags);
        loadExceptionTable(methodEntity, method, methodDefIndex, methodArea);

        return method;
    }

    private void loadExceptionTable(MTHEntity methodEntity, MethodDefinition method, int methodDefIndex, MethodArea methodArea) 
            throws LoadingException, IOException {
        // get exception table
        MTHEntity methodDef = methodEntity;
        AttrCode codeAttribute = methodDef.getCodeAttribute();
        AttrExcTableItem[] attrExceptionTable = (codeAttribute != null) ? codeAttribute.exceptionTable : null;

        // iterate over exception table
        if (attrExceptionTable != null) {
            MethodExcTableItem[] methodExceptionTable = new MethodExcTableItem[attrExceptionTable.length];

            for (int i = 0; i < attrExceptionTable.length; i++) {
                AttrExcTableItem attrItem = attrExceptionTable[i];

                // copy exctable from codeAttribute into method definition
                MethodExcTableItem item = new MethodExcTableItem();
                item.startPc = attrItem.startPc;
                item.endPc = attrItem.endPc;
                item.handlerPc = attrItem.handlerPc;

                if (attrItem.catchType != 0) {
                    // not finally block
                    int catchClassIndex = ((CPClass) cpEntities[attrItem.catchType]).nameIndex;
                    String catchClass = ((CPUtf8) cpEntities[catchClassIndex]).value;
                    ClassFile cls = methodArea.getClassFile(catchClass);
                    item.catchClass = cls;
                }
                methodExceptionTable[i] = item;
            }
            method.setExceptionTable(methodExceptionTable);
        }
    }

    /**
     * Loads field definition from constant pool according to selected index
     */
    public FieldDefinition getFieldDefinition(int fieldRefIndex) throws LoadingException {
        if (cpEntities[fieldRefIndex].tag != ConstantPoolType.CPT_Fieldref) {
            throw new LoadingException("Wrong field index");
        }

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
     * Gets an index of a method definition
     */
    public int getMethodDefIndex(String methodName, String methodDescriptor) {
        for (int i = 0; i < methodInfos.length; ++i) {
            if (((CPUtf8) cpEntities[methodInfos[i].nameIndex]).value.equals(methodName)
                    && ((CPUtf8) cpEntities[methodInfos[i].descriptorIndex]).value.equals(methodDescriptor)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Gets a name and a descriptor by cp (constantPool) index
     */
    public NameDesc getNameAndDescriptorByCpIndex(int cpIndex) {
        CPFieldRef fieldRef = (CPFieldRef) cpEntities[cpIndex];
        CPNameAndType nameAndType = (CPNameAndType) cpEntities[fieldRef.nameAndTypeIndex];
        String name = ((CPUtf8) cpEntities[nameAndType.nameIndex]).value;
        String descriptor = ((CPUtf8) cpEntities[nameAndType.descriptorIndex]).value;

        NameDesc nm = new NameDesc();
        nm.name = name;
        nm.descriptor = descriptor;
        return nm;
    }

    /**
     * Gets a field info based on its name, descriptor and a constant pool index
     */
    public FLEntity getFieldInfo(String name, String descriptor, int cpIndex) throws LoadingException {

        if (fieldInfosByCpIndex == null) {
            fieldInfosByCpIndex = new HashMap<>();
        } else if (fieldInfosByCpIndex.containsKey(cpIndex)) {
            return fieldInfosByCpIndex.get(cpIndex);
        }

        ClassFile _classFile = this;
        FLEntity fieldInfo;
        while ((fieldInfo = _classFile.getFieldInfo(name, descriptor)) == null && _classFile.getSuperclassName() != null) {
            _classFile = _classFile.getSuperClass();
        }

        fieldInfosByCpIndex.put(cpIndex, fieldInfo);
        return fieldInfo;
    }

    private boolean constructed = false;

    /**
     * Initialises a class, invoking <clinit> method
     */
    public void constructClass(JVMStack stack, MethodArea methodArea) throws LoadingException, Exception {
        if (!constructed) {
            constructed = true;
            JVMLogger.log(JVMLogger.TAG_OTHER, "Initializing class " + className);
            int methodDef = this.getMethodDefIndex("<clinit>", "()V");

            // no <clinit> method available
            if (methodDef == -1) {
                return;
            }

            MethodDefinition method = this.getMethodDefinition(methodDef, methodArea, className, "<clinit>", "()V");
            StackFrame initFrame = new StackFrame(this, methodDef, method, stack.jvmThread);
            stack.push(initFrame);
            stack.jvmThread.getInstructionManager().runInstruction(initFrame.getNextInstruction());
        }
    }

    /**
     * Gets true, if the classfile has at least one native method Objects with
     * native methods are usually connected with own implementations in
     * compactjvm.natives package
     */
    public boolean hasNativeMethods() {
        for (MTHEntity ent : this.methodInfos) {
            if (ent.isNativeMethod()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets field index by given name and a descriptor
     */
    private FLEntity getFieldInfo(String fieldName, String fieldDescriptor) throws LoadingException {
        for (FLEntity fieldInfo : fieldInfos) {
            if (((CPUtf8) cpEntities[fieldInfo.nameIndex]).value.equals(fieldName) && 
                    ((CPUtf8) cpEntities[fieldInfo.descriptorIndex]).value.equals(fieldDescriptor)) {
                return fieldInfo;
            }
        }
        return null;
    }
}

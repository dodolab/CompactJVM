package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.parsing.WordParser;
import cz.cvut.fit.compactjvm.classfile.FLEntity;
import cz.cvut.fit.compactjvm.classfile.NameDesc;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.JVMStack;
import cz.cvut.fit.compactjvm.jvm.ObjectHeap;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;

/**
 * Set field in object
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.getfield
 * @author Nick Nemame
 */
public class GetfieldInstruction {
    
    public static final int PARAM_COUNT = 2;
    
    public static void run(JVMStack stack, ObjectHeap heap) throws LoadingException {
        StackFrame stackFrame = stack.getCurrentFrame();
        byte[] bytes = stackFrame.loadInstructionParams(PARAM_COUNT);
        int cpIndex = WordParser.fromByteArray(bytes);
        
        FLEntity fieldInfo;
        
        
        SObjectRef reference = stackFrame.operandStack.pop();
        NameDesc nd = stackFrame.associatedClass.getNameAndDescriptorByCpIndex(cpIndex);
        fieldInfo = stackFrame.associatedClass.getFieldInfo(nd.name, nd.descriptor, cpIndex);
        
        // search in reference classFile
        if(fieldInfo == null){
           fieldInfo = reference.getClassFile().getFieldInfo(nd.name, nd.descriptor, cpIndex);
        }
        
        SStruct value = heap.readFromHeap(reference.getReference(), fieldInfo.dataFieldOffset);
        stackFrame.operandStack.push(value);
        
        JVMLogger.log(JVMLogger.TAG_INSTR, "Get field from heap (reference: "+reference+", value: "+value+")");
    }

}

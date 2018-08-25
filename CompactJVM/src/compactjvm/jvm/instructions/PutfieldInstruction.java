package compactjvm.jvm.instructions;

import compactjvm.parsing.WordParser;
import compactjvm.classfile.FLEntity;
import compactjvm.classfile.NameDesc;
import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.JVMStack;
import compactjvm.jvm.MethodArea;
import compactjvm.jvm.ObjectHeap;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 * Set field in object
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.putfield
 * @author Adam Vesecky
 */
public class PutfieldInstruction {
    
    public static final int PARAM_COUNT = 2;
    
    public static void run(JVMStack stack, ObjectHeap heap, MethodArea methodArea) throws LoadingException {
        StackFrame stackFrame = stack.getCurrentFrame();
        byte[] bytes = stackFrame.loadInstructionParams(PARAM_COUNT);
        int cpIndex = WordParser.fromByteArray(bytes);
        
        SStruct value = stackFrame.operandStack.pop();
        SObjectRef reference = stackFrame.operandStack.pop();
        
        NameDesc nd = stackFrame.associatedClass.getNameAndDescriptorByCpIndex(cpIndex);
        FLEntity fieldInfo = stackFrame.associatedClass.getFieldInfo(nd.name, nd.descriptor, cpIndex);
        
        heap.writeToHeap(reference.getReference(), fieldInfo.dataFieldOffset, value);
        JVMLogger.log(JVMLogger.TAG_INSTR_STORE, "Put field to heap: (reference: "+reference+", value: "+value+")");
    }
}

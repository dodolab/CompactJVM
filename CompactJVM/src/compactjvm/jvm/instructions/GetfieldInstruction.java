package compactjvm.jvm.instructions;

import compactjvm.parsing.WordParser;
import compactjvm.classfile.FLEntity;
import compactjvm.classfile.NameDesc;
import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.JVMStack;
import compactjvm.jvm.ObjectHeap;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 * Set field in object
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.getfield
 * @author Adam Vesecky
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
        
        JVMLogger.log(JVMLogger.TAG_INSTR_LOAD, "Get field from heap (reference: "+reference+", value: "+value+")");
    }

}

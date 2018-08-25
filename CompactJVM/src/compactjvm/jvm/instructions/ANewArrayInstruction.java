package compactjvm.jvm.instructions;

import compactjvm.classfile.ClassFile;
import compactjvm.parsing.WordParser;
import compactjvm.cpentities.CPClass;
import compactjvm.cpentities.CPUtf8;
import compactjvm.exceptions.LoadingException;
import compactjvm.exceptions.OutOfHeapMemException;
import compactjvm.jvm.MethodArea;
import compactjvm.jvm.ObjectHeap;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.SArrayRef;
import compactjvm.structures.SInt;
import java.io.IOException;

/**
 * Instruction for allocating object array
 * 
 * @author Adam Vesecky
 */
public class ANewArrayInstruction {
 
    
    public static void run(StackFrame stackFrame, MethodArea methodArea, ObjectHeap heap) throws LoadingException, OutOfHeapMemException, IOException{

        // get class name 
        byte[] bytes = stackFrame.loadInstructionParams(2);
        int cpIndex = WordParser.fromByteArray(bytes);
        int classNameIndex = ((CPClass) stackFrame.associatedClass.cpEntities[cpIndex]).nameIndex;
        String className = ((CPUtf8) stackFrame.associatedClass.cpEntities[classNameIndex]).value;
        ClassFile cls = methodArea.getClassFile(className);
        
        // get array size
        SInt size = stackFrame.operandStack.pop();
        
        if(size.getValue() < 0) throw new LoadingException("Array size is lower than 0 !!");
        
        SArrayRef arrayReference = heap.allocObjectArray(cls, size.getValue());
        stackFrame.operandStack.push(arrayReference);
        JVMLogger.log(JVMLogger.TAG_INSTR_STORE, "New object array: "+className); 
    } 
}

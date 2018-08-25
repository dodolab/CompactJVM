package compactjvm.jvm.instructions;

import compactjvm.classfile.ClassFile;
import compactjvm.definitions.Instruction;
import compactjvm.exceptions.OutOfHeapMemException;
import compactjvm.jvm.JVMLogger;
import compactjvm.jvm.JVMStack;
import compactjvm.jvm.MethodArea;
import compactjvm.jvm.ObjectHeap;
import compactjvm.jvm.StackFrame;
import compactjvm.structures.SObjectRef;

/**
 *
 * Instruction that moves exception thrown in CompactJVM into running program
 * 
 * @author Adam Vesecky
 */
public class AAAException {
    
    public static void throwException(Exception exc, JVMStack stack, ObjectHeap heap,  MethodArea methodArea) throws OutOfHeapMemException, Exception{
        
        exc.printStackTrace();
        JVMLogger.log(JVMLogger.TAG_OTHER, "Throwing exception from outside: " + exc.getClass().getName()+" -> "+exc.getMessage());
        

        StackFrame current = stack.getCurrentFrame();

        // create exception 
        ClassFile cls = methodArea.getClassFile("java/lang/Exception");
        SObjectRef objectReference = heap.allocObject(cls);

        // create message
        String stringText = exc.getMessage();
        SObjectRef strRef = stack.jvmThread.getNativeArea().writeStringToHeap(stringText);
        
        // set message to exception
        heap.writeToHeap(objectReference.getReference(), 0, strRef);
        
        // invoke AThrow
        current.operandStack.push(objectReference);
        stack.jvmThread.getInstructionManager().runInstruction((byte)Instruction.IN_ATHROW);
        
    }
    
}

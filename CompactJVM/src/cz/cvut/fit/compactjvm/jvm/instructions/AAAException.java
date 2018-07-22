package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.classfile.ClassFile;
import cz.cvut.fit.compactjvm.definitions.Instruction;
import cz.cvut.fit.compactjvm.exceptions.OutOfHeapMemException;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.jvm.JVMStack;
import cz.cvut.fit.compactjvm.jvm.MethodArea;
import cz.cvut.fit.compactjvm.jvm.ObjectHeap;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.structures.SObjectRef;

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

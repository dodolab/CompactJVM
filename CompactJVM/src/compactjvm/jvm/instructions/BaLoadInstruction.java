package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.ObjectHeap;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 * load a byte from an array
 * @author Adam Vesecky
 */
public class BaLoadInstruction {

    public static void run(StackFrame stackFrame, ObjectHeap heap) throws LoadingException{
        
        SInt index = stackFrame.operandStack.pop();
        SArrayRef arrayRef = stackFrame.operandStack.pop();
        
        SByte value = heap.readFromHeap(arrayRef.getReference(), index.getValue());
        stackFrame.operandStack.push(value);
        
        JVMLogger.log(JVMLogger.TAG_INSTR_LOAD, "BaLoad: array["+index+"] : "+value);
    }
}


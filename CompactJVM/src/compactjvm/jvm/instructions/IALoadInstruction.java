package compactjvm.jvm.instructions;

import compactjvm.exceptions.ArrayOutOfBoundsException;
import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.ObjectHeap;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 * load an int from an array
 * @author Adam Vesecky
 */
public class IALoadInstruction {

    public static void run(StackFrame stackFrame, ObjectHeap heap) throws LoadingException, ArrayOutOfBoundsException{
        
        SInt index = stackFrame.operandStack.pop();
        SArrayRef arrayRef = stackFrame.operandStack.pop();
        
        if(index.getValue() >= arrayRef.getSize()) throw new ArrayOutOfBoundsException("Maximum index is "+(arrayRef.getSize() - 1)+", "+index.getValue()+" given.");
        
        SInt value = heap.readFromHeap(arrayRef.getReference(), index.getValue());
        stackFrame.operandStack.push(value);
        
        JVMLogger.log(JVMLogger.TAG_INSTR_LOAD, "IALoad: array["+index+"] : "+value);
    }
}

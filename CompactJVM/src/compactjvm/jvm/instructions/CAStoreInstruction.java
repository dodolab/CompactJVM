package compactjvm.jvm.instructions;

import compactjvm.exceptions.ArrayOutOfBoundsException;
import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.ObjectHeap;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 * store an int into an array
 * @author Adam Vesecky
 */
public class CAStoreInstruction {
    
    public static void run(StackFrame stackFrame, ObjectHeap heap) throws LoadingException, ArrayOutOfBoundsException{

        SChar valueToAdd = stackFrame.operandStack.pop();
        SInt index = stackFrame.operandStack.pop();
        SArrayRef arrayRef = stackFrame.operandStack.pop();;
        
        if(index.getValue() >= arrayRef.getSize()) throw new ArrayOutOfBoundsException("Maximum index is "+(arrayRef.getSize() - 1)+", "+index.getValue()+" given.");
        
        heap.writeToHeap(arrayRef.getReference(), index.getValue(), valueToAdd);
        
        JVMLogger.log(JVMLogger.TAG_INSTR_STORE,"CAStore: array["+index+"] = "+valueToAdd);  
    }
}

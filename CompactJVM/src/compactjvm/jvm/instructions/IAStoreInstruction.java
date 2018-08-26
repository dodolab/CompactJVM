package compactjvm.jvm.instructions;

import compactjvm.exceptions.ArrayOutOfBoundsException;
import compactjvm.exceptions.LoadingException;
import compactjvm.exceptions.OutOfHeapMemException;
import compactjvm.jvm.ObjectHeap;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.jvm.MethodArea;
import compactjvm.structures.*;

/**
 * store an int into an array
 * @author Adam Vesecky
 */
public class IAStoreInstruction {
    
    public static void run(StackFrame stackFrame, ObjectHeap heap, MethodArea methodArea) throws LoadingException, ArrayOutOfBoundsException, OutOfHeapMemException, Exception{

        SInt valueToAdd = stackFrame.operandStack.pop();
        SInt index = stackFrame.operandStack.pop();
        SArrayRef arrayRef = stackFrame.operandStack.pop();
        
        if(index.getValue() >= arrayRef.getSize()){
            // throw an exception directly into the running program
            AAAException.throwException(new ArrayOutOfBoundsException("Maximum index is "+(arrayRef.getSize() - 1)+", "+index.getValue()+" given.")
                    , stackFrame.jvmThread.getStack(), heap, methodArea);
                        return;
        }
        
        heap.writeToHeap(arrayRef.getReference(), index.getValue(), valueToAdd);
        JVMLogger.log(JVMLogger.TAG_INSTR_STORE,"IAStore: array["+index+"] = "+valueToAdd);
    }
}

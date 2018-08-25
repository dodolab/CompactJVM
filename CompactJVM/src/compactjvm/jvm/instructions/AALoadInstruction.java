package compactjvm.jvm.instructions;

import compactjvm.exceptions.ArrayOutOfBoundsException;
import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.JVMLogger;
import compactjvm.jvm.ObjectHeap;
import compactjvm.jvm.StackFrame;
import compactjvm.structures.SArrayRef;
import compactjvm.structures.SGenericRef;
import compactjvm.structures.SInt;

/**
 * Loads reference from array (0x32)
 * 
 * @author Adam Vesecky
 */
public class AALoadInstruction {
    
    public static void run(StackFrame stackFrame, ObjectHeap heap) throws LoadingException, ArrayOutOfBoundsException{
        // for instruction ALOAD where index is specified by parameter
        SInt index = stackFrame.operandStack.pop();
        SArrayRef arrayRef = stackFrame.operandStack.pop();
        
        if(index.getValue() >= arrayRef.getSize()) throw new ArrayOutOfBoundsException("Maximum index is "+(arrayRef.getSize() - 1)+", "+index.getValue()+" given.");
        
        SGenericRef[] arr = heap.readObjectArrayFromHeap(arrayRef.getReference());
        stackFrame.operandStack.push(arr[index.getValue()]);
        
        JVMLogger.log(JVMLogger.TAG_INSTR_LOAD, "AALoad: object array: "+arrayRef.getArrayType().className+"["+index.getValue()+"] -> "+arr[index.getValue()]);
    }
}

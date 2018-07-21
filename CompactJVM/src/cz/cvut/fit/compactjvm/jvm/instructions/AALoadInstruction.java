package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.ArrayOutOfBoundsException;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.jvm.ObjectHeap;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.structures.SArrayRef;
import cz.cvut.fit.compactjvm.structures.SGenericRef;
import cz.cvut.fit.compactjvm.structures.SInt;

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
        
        JVMLogger.log(JVMLogger.TAG_INSTR, "AALoad: object array: "+arrayRef.getArrayType().className+"["+index.getValue()+"] -> "+arr[index.getValue()]);
    }
}

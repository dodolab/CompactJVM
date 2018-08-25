package compactjvm.jvm.instructions;

import compactjvm.exceptions.ArrayOutOfBoundsException;
import compactjvm.exceptions.LoadingException;
import compactjvm.exceptions.OutOfHeapMemException;
import compactjvm.jvm.ObjectHeap;
import compactjvm.jvm.StackFrame;
import compactjvm.structures.SArrayRef;
import compactjvm.structures.SGenericRef;
import compactjvm.structures.SInt;
import compactjvm.structures.SObjectRef;
import compactjvm.jvm.JVMLogger;
import compactjvm.jvm.MethodArea;

/**
 * 0x53 -> Store into reference array
 * 
 * @author Adam Vesecky
 */
public class AAStoreInstruction {
    
    public static void run(StackFrame stackFrame, ObjectHeap heap, MethodArea methodArea) throws LoadingException, ArrayOutOfBoundsException, OutOfHeapMemException, Exception{
        
        SObjectRef value = stackFrame.operandStack.pop();
        SInt index = stackFrame.operandStack.pop();
        SArrayRef arrayRef = stackFrame.operandStack.pop();
        
        if(index.getValue() >= arrayRef.getSize()){
            // throw exception inside
            AAAException.throwException(new ArrayOutOfBoundsException("Maximum index is "+(arrayRef.getSize() - 1)+", "+index.getValue()+" given.")
                    , stackFrame.jvmThread.getStack(), heap, methodArea);
            return;
        }
        
        heap.writeToHeap(arrayRef.getReference(), index.getValue(), value);
        
        JVMLogger.log(JVMLogger.TAG_INSTR_STORE, "AAStoreN: object array: "+arrayRef.getArrayType().className+"["+index.getValue()+"] = "+value);
    }
}

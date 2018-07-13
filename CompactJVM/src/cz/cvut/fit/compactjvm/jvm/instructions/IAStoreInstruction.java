/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.ArrayOutOfBoundsException;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.ObjectHeap;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;

/**
 * store an int into an array
 * @author Adam Vesecky
 */
public class IAStoreInstruction {
    
    public static void run(StackFrame stackFrame, ObjectHeap heap) throws LoadingException, ArrayOutOfBoundsException{

        SInt valueToAdd = stackFrame.operandStack.pop();
        SInt index = stackFrame.operandStack.pop();
        SArrayRef arrayRef = stackFrame.operandStack.pop();;
        
        if(index.getValue() >= arrayRef.getSize()) throw new ArrayOutOfBoundsException("Maximum index is "+(arrayRef.getSize() - 1)+", "+index.getValue()+" given.");
        
        heap.writeToHeap(arrayRef.getReference(), index.getValue(), valueToAdd);
        
        JVMLogger.log(JVMLogger.TAG_INSTR,"IAStore: array["+index+"] = "+valueToAdd);
        
    }

}

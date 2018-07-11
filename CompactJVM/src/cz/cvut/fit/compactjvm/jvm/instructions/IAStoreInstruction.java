/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.ObjectHeap;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.logging.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;

/**
 * store an int into an array
 * @author Adam Vesecky
 */
public class IAStoreInstruction {
    
    public static void run(StackFrame stackFrame, ObjectHeap heap) throws LoadingException{

        SInt valueToAdd = stackFrame.operandStack.pop();
        SInt index = stackFrame.operandStack.pop();
        SGenericRef arrayRef = stackFrame.operandStack.pop();
        
        heap.writeToHeap(arrayRef.getReference(), index.getValue(), valueToAdd);
        
        JVMLogger.log(JVMLogger.TAG_INSTR,"IAStore: array["+index+"] = "+valueToAdd);
        
    }

}

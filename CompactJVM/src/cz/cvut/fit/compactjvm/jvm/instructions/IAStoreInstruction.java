/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.jvm.ObjectHeap;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.logging.JVMLogger;

/**
 * store an int into an array
 * @author Adam Vesecky
 */
public class IAStoreInstruction {
    
    public static void run(StackFrame stackFrame, ObjectHeap heap) {

        int valueToAdd = stackFrame.operandStack.popInt();
        int index = stackFrame.operandStack.popInt();
        int arrayRef = stackFrame.operandStack.popInt();
        
        heap.writeToHeap(arrayRef, index, valueToAdd);
        
        JVMLogger.log(JVMLogger.TAG_INSTR,"IAStore: array["+index+"] = "+valueToAdd);
        
    }

}

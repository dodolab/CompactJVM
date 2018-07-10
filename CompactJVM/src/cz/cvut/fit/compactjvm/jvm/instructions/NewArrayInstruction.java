/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.exceptions.OutOfHeapMemException;
import cz.cvut.fit.compactjvm.jvm.AnotherGarbageCollector;
import cz.cvut.fit.compactjvm.jvm.ObjectHeap;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.logging.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;

/**
 * create a new array of references of length count and component type identified by the class reference index (indexbyte1 << 8 + indexbyte2) in the constant pool
 * @author Adam Vesecky
 */
public class NewArrayInstruction {
    
    public static void run(StackFrame stackFrame, ObjectHeap heap) throws LoadingException, ClassNotFoundException, OutOfHeapMemException {
        
        int arrayType = stackFrame.getNextInstruction();

        SInt size = stackFrame.operandStack.pop();
        
        if(size.getValue() < 0) throw new LoadingException("Array size cant' be lower than 0");
        
        SArrayRef arrayReference = heap.allocArray(1, size.getValue());
        
        stackFrame.operandStack.push(arrayReference);
        
        JVMLogger.log(JVMLogger.TAG_INSTR, "NewArray: size "+size);
    }
}

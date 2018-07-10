/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.logging.JVMLogger;

/**
 * create a new array of references of length count and component type identified by the class reference index (indexbyte1 << 8 + indexbyte2) in the constant pool
 * @author Adam Vesecky
 */
public class NewArrayInstruction {
    
    public static void run(StackFrame stackFrame) throws LoadingException {
        
        int arrayType = stackFrame.getNextInstruction();

        int size = stackFrame.operandStack.popByte();
        
        if(size < 0) throw new LoadingException("Array size cant' be lower than 0");
        
        int[] arr = new int[size];
        int arrayReference = stackFrame.arrayStorage.storeArray(arr);
        stackFrame.operandStack.pushInt(arrayReference);
        
        JVMLogger.log(JVMLogger.TAG_INSTR, "NewArray: size "+size);
    }
}

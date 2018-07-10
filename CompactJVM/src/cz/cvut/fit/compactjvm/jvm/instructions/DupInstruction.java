/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.logging.JVMLogger;

/**
 * push the long #value onto the stack
 * @author Adam Vesecky
 */
public class DupInstruction {
    
    public static void run(StackFrame stackFrame) {
        
        int value = stackFrame.operandStack.popInt();
        stackFrame.operandStack.pushInt(value);
        stackFrame.operandStack.pushInt(value);
        
        JVMLogger.log(JVMLogger.TAG_INSTR, "Dup: "+value);
    }
}

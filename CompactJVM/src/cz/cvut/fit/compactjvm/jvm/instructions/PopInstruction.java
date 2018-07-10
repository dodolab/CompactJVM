/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.jvm.OperandStack;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.logging.JVMLogger;

/**
 * perform no operation
 * @author Adam Vesecky
 */
public class PopInstruction {
    
    public static void run(StackFrame stackFrame) {

        stackFrame.operandStack.popInt();
        
        JVMLogger.log(JVMLogger.TAG_INSTR, "Pop; Pop from stack");
    }
    
}

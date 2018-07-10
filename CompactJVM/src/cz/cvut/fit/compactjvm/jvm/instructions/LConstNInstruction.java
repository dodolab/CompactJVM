/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.logging.JVMLogger;

/**
 *
 * @author Adam Vesecky
 */
public class LConstNInstruction {
    
    public static void run(StackFrame stackFrame, int value) {
        JVMLogger.log(JVMLogger.TAG_INSTR, "LConst: "+value);
        
        // push long
        stackFrame.operandStack.pushInt(0);
        stackFrame.operandStack.pushInt(value);
    }
}
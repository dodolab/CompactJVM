/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.logging.JVMLogger;

/**
 * Set static field in class
 * @author Nick Nemame
 */
public class PutStaticInstruction {
    
    public static final int PARAM_COUNT = 2;
    
    public static void run(StackFrame stackFrame) {
        //byte[] fieldRefIndexBytes = stackFrame.loadInstructionParams(PARAM_COUNT);
        //int fieldRefIndex = 
        //int value = stackFrame.operandStack.popInt();
        JVMLogger.log(JVMLogger.TAG_INSTR, "PutStatic: TODO");
        //stackFrame.operandStack.pushInt(value);
    }
    
}

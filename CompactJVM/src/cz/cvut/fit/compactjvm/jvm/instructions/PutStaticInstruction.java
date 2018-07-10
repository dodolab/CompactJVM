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
 * @author Nick Nemame
 */
public class PutStaticInstruction {
    
    public static void run(StackFrame stackFrame) {
        byte localVariableIndex = stackFrame.loadInstructionSingleParam();
        int value = stackFrame.localVariables.getInt(localVariableIndex);
        JVMLogger.log(JVMLogger.TAG_INSTR, "PutStatic: "+value);
        stackFrame.operandStack.pushInt(value);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.jvm.StackFrame;

/**
 *
 * @author Nick Nemame
 */
public class PutStaticInstruction {
    
    public static void run(StackFrame stackFrame) {
        byte localVariableIndex = stackFrame.loadInstructionSingleParam();
        int value = stackFrame.localVariables.getInt(localVariableIndex);
        stackFrame.operandStack.pushInt(value);
    }
    
}

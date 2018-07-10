/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.jvm.MethodArea;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.logging.JVMLogger;

/**
 * Store int into local variable. int value is popped from the operand stack.
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.istore
 * @author Nick Nemame
 */
public class IStoreInstruciton {
    
    /**
     * 
     * @param stackFrame 
     */
    public static void run(StackFrame stackFrame) {
        byte localVariableIndex = stackFrame.loadInstructionSingleParam();
        int value = stackFrame.operandStack.popInt();
        JVMLogger.log(JVMLogger.TAG_INSTR, "IStore: "+value);
        stackFrame.localVariables.setInt(localVariableIndex, value);
    }

}

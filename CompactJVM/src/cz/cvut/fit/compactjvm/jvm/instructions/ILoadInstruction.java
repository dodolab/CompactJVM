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
 * Load int from local variable and push onto the operand stack
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.iload
 * @author Nick Nemame
 */
public class ILoadInstruction {
    
    public static void run(StackFrame stackFrame) {
        byte localVariableIndex = stackFrame.loadInstructionSingleParam();
        int value = stackFrame.localVariables.getInt(localVariableIndex);
        JVMLogger.log(JVMLogger.TAG_INSTR, "ILoad: "+value);
        stackFrame.operandStack.pushInt(value);
    }

}

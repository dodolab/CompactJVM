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
 * Push int constant onto the operand stack
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.iconst_i
 * @author Nick Nemame
 */
public class IConstIInstruction {
    
    public static void run(StackFrame stackFrame, int value) {
        JVMLogger.log(JVMLogger.TAG_INSTR, "IConst: "+value);
        stackFrame.operandStack.pushInt(value);
    }

}

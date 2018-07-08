/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.jvm.MethodArea;
import cz.cvut.fit.compactjvm.jvm.StackFrame;

/**
 * Add int
 * The values are popped from the operand stack.
 * The int result is value1 + value2. The result is pushed onto the operand stack.
 * Despite the fact that overflow may occur, execution of an iadd instruction never throws a run-time exception.
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.iadd
 * @author Nick Nemame
 */
public class IAddInstruction {
    
    public static void run(StackFrame stackFrame) {
        int value = stackFrame.operandStack.popInt() + stackFrame.operandStack.popInt();
        stackFrame.operandStack.pushInt(value);
    }

}

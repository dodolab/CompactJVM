package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.ArrayOutOfBoundsException;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.jvm.MethodArea;
import cz.cvut.fit.compactjvm.jvm.ObjectHeap;
import cz.cvut.fit.compactjvm.structures.*;

/**
 * Divide int The values are popped from the operand stack. The int result is
 * value1 / value2. The result is pushed onto the operand stack.
 *
 * @author Adam Vesecky
 */
public class IDivInstruction {

    public static void run(StackFrame stackFrame, ObjectHeap heap, MethodArea methodArea) throws LoadingException, Exception {
        SInt operand1 = stackFrame.operandStack.pop();
        SInt operand2 = stackFrame.operandStack.pop();
        try {
            JVMLogger.log(JVMLogger.TAG_INSTR, "IDiv: " + operand1 + " / " + operand2);
            SInt value = new SInt(operand2.getValue() / operand1.getValue());
            stackFrame.operandStack.push(value);
        } catch (ArithmeticException e) {
            AAAException.throwException(e, stackFrame.jvmThread.getStack(), heap, methodArea);
            return;
        }

    }
}

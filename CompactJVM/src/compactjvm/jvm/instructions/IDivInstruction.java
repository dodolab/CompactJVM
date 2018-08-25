package compactjvm.jvm.instructions;

import compactjvm.exceptions.ArrayOutOfBoundsException;
import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.jvm.MethodArea;
import compactjvm.jvm.ObjectHeap;
import compactjvm.structures.*;

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

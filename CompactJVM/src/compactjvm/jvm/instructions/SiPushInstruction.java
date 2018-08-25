package compactjvm.jvm.instructions;

import compactjvm.parsing.WordParser;
import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.SInt;

/**
 * Store short into local variable. int value is popped from the operand stack.
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.istore
 * @author Adam Vesecky
 */
public class SiPushInstruction {

    public static void run(StackFrame stackFrame) throws LoadingException{
        byte[] bytes = stackFrame.loadInstructionParams(2);
        int value = WordParser.fromByteArray(bytes);
        JVMLogger.log(JVMLogger.TAG_INSTR_PUSH, "SiPush: "+value);
        stackFrame.operandStack.push(new SInt(value));
    }
}

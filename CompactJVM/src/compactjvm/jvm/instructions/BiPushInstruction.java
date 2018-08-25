package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;
/**
 * Store int into local variable. int value is popped from the operand stack.
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.istore
 * @author Adam Vesecky
 */
public class BiPushInstruction {
    
    public static void run(StackFrame stackFrame) throws LoadingException{
        byte value = stackFrame.loadInstructionSingleParam();
        JVMLogger.log(JVMLogger.TAG_INSTR, "BiPush: "+value);
        stackFrame.operandStack.push(new SInt(value));
    }

}

package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 * Push int constant onto the operand stack
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.iconst_i
 * @author Adam Vesecky
 */
public class IConstIInstruction {
    
    public static void run(StackFrame stackFrame, int value) throws LoadingException{
        JVMLogger.log(JVMLogger.TAG_INSTR_PUSH, "IConst: "+value);
        stackFrame.operandStack.push(new SInt(value));
    }
}

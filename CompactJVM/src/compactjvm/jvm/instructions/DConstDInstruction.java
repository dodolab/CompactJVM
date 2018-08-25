package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 * Push int constant onto the operand stack
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.dconst_d
 * @author Adam Vesecky
 */
public class DConstDInstruction {
    
    public static void run(StackFrame stackFrame, double doubleValue) throws LoadingException{
        JVMLogger.log(JVMLogger.TAG_INSTR_STORE, "DConstD - value:"+doubleValue);
        SDouble dValue = new SDouble(doubleValue);
        stackFrame.operandStack.push(dValue);
    }
}

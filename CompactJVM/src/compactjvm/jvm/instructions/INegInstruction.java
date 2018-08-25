package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 * Load int from local variable and push onto the operand stack
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.ineg
 * @author Adam Vesecky
 */
public class INegInstruction {
    
    public static void run(StackFrame stackFrame) throws LoadingException{
        
        SIntable value = stackFrame.operandStack.pop();
        SIntable copyValue = (SIntable) value.makeCopy();
        copyValue.negate();
        stackFrame.operandStack.push(copyValue);
        JVMLogger.log(JVMLogger.TAG_INSTR_LOAD, "INeg: result value "+copyValue.toInt().getValue());
    }
}

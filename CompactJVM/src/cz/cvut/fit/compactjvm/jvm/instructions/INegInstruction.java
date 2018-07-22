package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;

/**
 * Load int from local variable and push onto the operand stack
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.ineg
 * @author Nick Nemame
 */
public class INegInstruction {
    
    public static void run(StackFrame stackFrame) throws LoadingException{
        
        SIntable value = stackFrame.operandStack.pop();
        value.negate();
        stackFrame.operandStack.push(value);
        JVMLogger.log(JVMLogger.TAG_INSTR_LOAD, "INeg: result value "+value.toInt().getValue());
    }
}

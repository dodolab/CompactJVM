package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;

/**
 * Push int constant onto the operand stack
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.dconst_d
 * @author Nick Nemame
 */
public class DConstDInstruction {
    
    public static void run(StackFrame stackFrame, double doubleValue) throws LoadingException{
        JVMLogger.log(JVMLogger.TAG_INSTR_STORE, "DConstD - value:"+doubleValue);
        SDouble dValue = new SDouble(doubleValue);
        stackFrame.operandStack.push(dValue);
    }
}

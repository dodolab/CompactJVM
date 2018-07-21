package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;

/**
 * Load long from local variable and push onto the operand stack
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.iload
 * @author Nick Nemame
 */
public class LLoadInstruction {
    
    public static void run(StackFrame stackFrame) throws LoadingException{
        byte localVariableIndex = stackFrame.loadInstructionSingleParam();
        SLong value = stackFrame.localVariables.getVar(localVariableIndex);
        JVMLogger.log(JVMLogger.TAG_INSTR_LOAD, "LLoad: "+value);
        stackFrame.operandStack.push(value);
    }
}

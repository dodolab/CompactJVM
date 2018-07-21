package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;

/**
 * Store int into local variable. int value is popped from the operand stack.
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.istore
 * @author Nick Nemame
 */
public class IStoreInstruction {
    

    public static void run(StackFrame stackFrame) throws LoadingException{
        byte localVariableIndex = stackFrame.loadInstructionSingleParam();
        SIntable value = stackFrame.operandStack.pop();

        JVMLogger.log(JVMLogger.TAG_INSTR_STORE, "IStore: "+value);
        stackFrame.localVariables.setVar(localVariableIndex, value);
    }
}

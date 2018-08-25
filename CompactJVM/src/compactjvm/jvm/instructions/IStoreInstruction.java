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
public class IStoreInstruction {
    

    public static void run(StackFrame stackFrame) throws LoadingException{
        byte localVariableIndex = stackFrame.loadInstructionSingleParam();
        SIntable value = stackFrame.operandStack.pop();

        JVMLogger.log(JVMLogger.TAG_INSTR_STORE, "IStore: "+value);
        stackFrame.localVariables.setVar(localVariableIndex, value);
    }
}

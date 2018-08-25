package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 * Load int from local variable and push onto the operand stack
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.iload
 * @author Adam Vesecky
 */
public class ILoadInstruction {
    
    public static void run(StackFrame stackFrame) throws LoadingException{
        byte localVariableIndex = stackFrame.loadInstructionSingleParam();
        SIntable value = stackFrame.localVariables.getVar(localVariableIndex);

        JVMLogger.log(JVMLogger.TAG_INSTR_LOAD, "ILoad: "+value);
        stackFrame.operandStack.push(value);
    }
}

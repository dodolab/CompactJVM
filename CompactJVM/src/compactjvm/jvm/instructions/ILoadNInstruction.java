package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 * Load int from local variable
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.iload_n
 * @author Adam Vesecky
 */
public class ILoadNInstruction {
    
    public static void run(StackFrame stackFrame, int localVariableIndex) throws LoadingException{
        SIntable value = stackFrame.localVariables.getVar(localVariableIndex);
        JVMLogger.log(JVMLogger.TAG_INSTR_LOAD, "ILoadN: "+value);
        stackFrame.operandStack.push(value);
    }
}

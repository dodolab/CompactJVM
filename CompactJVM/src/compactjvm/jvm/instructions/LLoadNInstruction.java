package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;


public class LLoadNInstruction {
    
    public static void run(StackFrame stackFrame, int localVariableIndex) throws LoadingException{
        SLong value = stackFrame.localVariables.getVar(localVariableIndex);
        JVMLogger.log(JVMLogger.TAG_INSTR_LOAD, "LLoadN: "+value);
        stackFrame.operandStack.push(value);
    }
}

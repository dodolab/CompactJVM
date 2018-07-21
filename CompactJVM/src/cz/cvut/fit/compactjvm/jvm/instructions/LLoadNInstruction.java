package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;


public class LLoadNInstruction {
    
    public static void run(StackFrame stackFrame, int localVariableIndex) throws LoadingException{
        SLong value = stackFrame.localVariables.getVar(localVariableIndex);
        JVMLogger.log(JVMLogger.TAG_INSTR_LOAD, "LLoadN: "+value);
        stackFrame.operandStack.push(value);
    }
}

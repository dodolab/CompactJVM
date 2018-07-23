package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;

/**
 * push the long #value onto the stack
 * @author Adam Vesecky
 */
public class DupInstruction {
    
    public static void run(StackFrame stackFrame) throws LoadingException{
        
        SObjectRef value = stackFrame.operandStack.pop();
        stackFrame.operandStack.push(value);
        stackFrame.operandStack.push(value);
        
        JVMLogger.log(JVMLogger.TAG_INSTR_PUSH, "Dup: "+value);
    }
}

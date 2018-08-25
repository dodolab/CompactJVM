package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

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

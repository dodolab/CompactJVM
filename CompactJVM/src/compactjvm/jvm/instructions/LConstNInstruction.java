package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 * push the long #value onto the stack
 * @author Adam Vesecky
 */
public class LConstNInstruction {
    
    public static void run(StackFrame stackFrame, int value) throws LoadingException{
        JVMLogger.log(JVMLogger.TAG_INSTR_PUSH, "LConst: "+value);
        
        // push long
        stackFrame.operandStack.push(new SLong(value));
    }
}

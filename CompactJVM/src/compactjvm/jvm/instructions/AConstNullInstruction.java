package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.structures.SObjectRef;
import compactjvm.jvm.JVMLogger;

/**
 * Push null (nothing more)
 * @author Adam Vesecky
 */
public class AConstNullInstruction {
    
    public static void run(StackFrame stackFrame) throws LoadingException{
        
        // just push null object
        stackFrame.operandStack.push(new SObjectRef());
        JVMLogger.log(JVMLogger.TAG_INSTR_PUSH, "AConstNull");
    }
    
}

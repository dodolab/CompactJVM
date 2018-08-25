package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;

/**
 * perform pop
 * @author Adam Vesecky
 */
public class PopInstruction {
    
    public static void run(StackFrame stackFrame) throws LoadingException{

        stackFrame.operandStack.pop();
        
        JVMLogger.log(JVMLogger.TAG_INSTR, "Pop; Pop from stack");
    }  
}

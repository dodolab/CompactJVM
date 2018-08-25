package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 *  push 0.0f, 1.0f or 2.0f on the stack
 * @author Adam Vesecky
 */
public class FConstInstruction {
    
    public static void run(StackFrame stackFrame, float value) throws LoadingException{
        JVMLogger.log(JVMLogger.TAG_INSTR_PUSH, "FConst: "+value);
        stackFrame.operandStack.push(new SFloat(value));
    }
}

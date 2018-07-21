package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;

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

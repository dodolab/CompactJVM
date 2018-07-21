package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;

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

package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;

/**
 * perform no operation
 * @author Adam Vesecky
 */
public class NopInstruction {
    
    public static void run(StackFrame stackFrame) {
        JVMLogger.log(JVMLogger.TAG_INSTR, "Nop; doing nothing useful");
    } 
}

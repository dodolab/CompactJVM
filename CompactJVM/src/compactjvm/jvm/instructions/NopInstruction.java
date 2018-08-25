package compactjvm.jvm.instructions;

import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;

/**
 * perform no operation
 * @author Adam Vesecky
 */
public class NopInstruction {
    
    public static void run(StackFrame stackFrame) {
        JVMLogger.log(JVMLogger.TAG_INSTR, "Nop; doing nothing useful");
    } 
}

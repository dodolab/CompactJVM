package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;

/**
 * goes to another instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)
 * @author Adam Vesecky
 */
public class GotoInstruction {
    
    public static void run(StackFrame stackFrame) {
        // two bytes
        stackFrame.loadInstructionSingleParam();
        byte val = stackFrame.loadInstructionSingleParam();
        int nextIns = stackFrame.getCurrentInstructionIndex() + val -3;
        stackFrame.setCurrentInstructionIndex(nextIns);
        
        JVMLogger.log(JVMLogger.TAG_INSTR, "Goto: "+nextIns);
    }
}

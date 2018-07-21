package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;

/**
 * goes to another instruction at branchoffset (signed short constructed from
 * unsigned bytes branchbyte1 << 8 + branchbyte2) @author Adam V
 *
 * esecky
 */
public class GotoInstruction {

    public static void run(StackFrame stackFrame) {

        int val = stackFrame.loadInstructionJumpAddr();

        stackFrame.setCurrentInstructionIndex(val);

        JVMLogger.log(JVMLogger.TAG_INSTR_JUMP, "Goto: " + val);
    }
}

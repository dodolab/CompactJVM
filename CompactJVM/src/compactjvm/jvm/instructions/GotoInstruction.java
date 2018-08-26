package compactjvm.jvm.instructions;

import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;

/**
 * goes to another instruction at branchoffset (signed short constructed from
 * unsigned bytes branchbyte1 << 8 + branchbyte2) 
 * 
 * @author Adam Vesecky
 */
public class GotoInstruction {

    public static void run(StackFrame stackFrame) {

        int val = stackFrame.loadInstructionJumpAddr();

        stackFrame.setCurrentInstructionIndex(val);

        JVMLogger.log(JVMLogger.TAG_INSTR_JUMP, "Goto: " + val);
    }
}

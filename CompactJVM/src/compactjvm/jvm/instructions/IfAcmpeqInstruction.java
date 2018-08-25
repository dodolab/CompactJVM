package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 * if references are equal, branch to instruction at branchoffset 
 * (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2) 
 * @author Adam Vesecky
 */
public class IfAcmpeqInstruction {

    public static void run(StackFrame stackFrame) throws LoadingException {

        int nextInstruction = stackFrame.loadInstructionJumpAddr();
        
        SGenericRef value2 = stackFrame.operandStack.pop();
        SGenericRef value1 = stackFrame.operandStack.pop();
        
        if (value2.getReference() == value2.getReference()) {
            stackFrame.setCurrentInstructionIndex(nextInstruction);
            JVMLogger.log(JVMLogger.TAG_INSTR_JUMP, "Ifacmpeq: " + value1 + " == " + value2 + "; goto " + nextInstruction);
        } else {
            JVMLogger.log(JVMLogger.TAG_INSTR_JUMP, "Ifacmpeq: " + value1 + " != " + value2);
        }
    }
}

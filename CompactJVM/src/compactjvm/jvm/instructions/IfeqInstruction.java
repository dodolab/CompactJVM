package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.SInt;

/**
 * if value is equal to 0, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)
 * @author Adam Vesecky
 */
public class IfeqInstruction {
     public static void run(StackFrame stackFrame) throws LoadingException{

        int nextInstruction = stackFrame.loadInstructionJumpAddr();
        
        SInt value = stackFrame.operandStack.pop();
        
        if(value.getValue() == 0){
            stackFrame.setCurrentInstructionIndex(nextInstruction);
            JVMLogger.log(JVMLogger.TAG_INSTR_JUMP, "Ifeq: "+value+" == 0; goto "+nextInstruction);
        }else{
            JVMLogger.log(JVMLogger.TAG_INSTR_JUMP, "Ifeq: "+value+" != 0");
        }
    }
}

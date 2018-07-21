package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.structures.SInt;

/**
 * if value is equal to 0, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)
 * @author Adam Vesecky
 */
public class IfneInstruction {
     public static void run(StackFrame stackFrame) throws LoadingException{

        int nextInstruction = stackFrame.loadInstructionJumpAddr();
        
        SInt value = stackFrame.operandStack.pop();
        
        if(value.getValue() != 0){
            stackFrame.setCurrentInstructionIndex(nextInstruction);
            JVMLogger.log(JVMLogger.TAG_INSTR, "Ifne: "+value+" != 0; goto "+nextInstruction);
        }else{
            JVMLogger.log(JVMLogger.TAG_INSTR, "Ifne: "+value+" == 0");
        }
    }
}

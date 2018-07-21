package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.structures.SGenericRef;

/**
 * If not null, goto...
 * @author Adam Vesecky
 */
public class IfnonnullInstruction {
    
    public static void run(StackFrame stackFrame) throws LoadingException{

        int nextInstruction = stackFrame.loadInstructionJumpAddr();
        
        SGenericRef value = stackFrame.operandStack.pop();
        
        if(!value.isNull()){
            stackFrame.setCurrentInstructionIndex(nextInstruction);
            JVMLogger.log(JVMLogger.TAG_INSTR_JUMP, "Ifnonnull: "+value+" is not null; goto "+nextInstruction);
        }else{
            JVMLogger.log(JVMLogger.TAG_INSTR_JUMP, "Ifnonnull: "+value+" is null");
        }
    }
}


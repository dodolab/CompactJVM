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

        // two bytes
        stackFrame.loadInstructionSingleParam();
        byte nextInstruction = stackFrame.loadInstructionSingleParam();
        
        SGenericRef value = stackFrame.operandStack.pop();
        
        if(!value.isNull()){
            JVMLogger.log(JVMLogger.TAG_INSTR, "Ifnonnull: "+value+" is not null; goto "+nextInstruction);
            stackFrame.setCurrentInstructionIndex(stackFrame.getCurrentInstructionIndex() + nextInstruction - 3);
        }else{
            JVMLogger.log(JVMLogger.TAG_INSTR, "Ifnonnull: "+value+" is null");
        }
    }
}


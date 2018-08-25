package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 *  load a reference onto the stack from a local variable #index
 * @author Adam Vesecky
 */
public class ALoadInstruction {

    public static void run(StackFrame stackFrame) throws LoadingException{
        // for instruction ALOAD where index is specified by parameter
        byte index = stackFrame.loadInstructionSingleParam();
        run(stackFrame, index);
    }
    
    public static void run(StackFrame stackFrame, int index) throws LoadingException {
        SGenericRef value = stackFrame.localVariables.getVar(index);
        stackFrame.operandStack.push(value);
        
        JVMLogger.log(JVMLogger.TAG_INSTR_LOAD, "ALoad; index: "+index+"; value: "+value);
        
    }

}

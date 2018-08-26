package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 * Store a reference into a local variable #index (or 0, 1, 2, 3)
 * @author Adam Vesecky
 */
public class AStoreNInstruction {
    
    public static void run(StackFrame stackFrame) throws LoadingException{
        int index = stackFrame.loadInstructionSingleParam();
        run(stackFrame, index);
    }
    
    public static void run(StackFrame stackFrame, int index) throws LoadingException{
        SGenericRef value = stackFrame.operandStack.pop();
        JVMLogger.log(JVMLogger.TAG_INSTR_STORE, "AStoreN: index: "+index+"; value:"+value);
        stackFrame.localVariables.setVar(index, value);
    }
}

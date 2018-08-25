package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 *  store int value into variable #index
 * @author Adam Vesecky
 */
public class IStoreNInstruction {
    

    public static void run(StackFrame stackFrame, int localVariableIndex) throws LoadingException{
        SIntable value = stackFrame.operandStack.pop(); //Int nebo char

        JVMLogger.log(JVMLogger.TAG_INSTR_STORE, "IStoreN: "+value);
        stackFrame.localVariables.setVar(localVariableIndex, value);
    }
}

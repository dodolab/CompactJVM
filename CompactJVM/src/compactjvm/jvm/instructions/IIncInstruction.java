package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 * increment local variable #index by signed byte const
 * @author Adam Vesecky
 */
public class IIncInstruction {
    
    public static void run(StackFrame stackFrame) throws LoadingException{
        int index = stackFrame.loadInstructionSingleParam();
        int constant = stackFrame.loadInstructionSingleParam();
        
        SInt localVar = stackFrame.localVariables.getVar(index);
        stackFrame.localVariables.setVar(index, new SInt(localVar.getValue()+constant));
        
        JVMLogger.log(JVMLogger.TAG_INSTR, "IInc: "+localVar+"+="+constant);
    }
}

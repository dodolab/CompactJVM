package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.JVMStack;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.SGenericRef;

/**
 * Return reference from method
 * @author Adam Vesecky
 */
public class AReturnInstruction {
    
    public static void run(JVMStack stack) throws LoadingException{
        
        SGenericRef valueToReturn = stack.getCurrentFrame().operandStack.pop();
        // pop everything
        while(!stack.getCurrentFrame().operandStack.isEmpty()) stack.getCurrentFrame().operandStack.pop();
        
        stack.removeCurrentFrame();
        stack.getCurrentFrame().operandStack.push(valueToReturn);
        
        JVMLogger.log(JVMLogger.TAG_INSTR, "AReturn: "+valueToReturn);
    }
}

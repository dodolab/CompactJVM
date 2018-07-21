package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.JVMStack;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.structures.SGenericRef;

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

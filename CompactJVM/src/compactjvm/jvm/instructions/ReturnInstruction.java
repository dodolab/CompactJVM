package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.JVMStack;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 * Return void from method. The interpreter then returns control to the invoker
 * of the method, reinstating the frame of the invoker.
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.return
 *
 * @author Adam Vesecky
 */
public class ReturnInstruction {


    public static void run(JVMStack stack) throws LoadingException{
        
        while(!stack.getCurrentFrame().operandStack.isEmpty()){
            stack.getCurrentFrame().operandStack.pop();
        }
        stack.removeCurrentFrame();
        JVMLogger.log(JVMLogger.TAG_INSTR, "Executed return instruction");
    }
}

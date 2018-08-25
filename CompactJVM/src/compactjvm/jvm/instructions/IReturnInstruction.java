package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.JVMStack;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 * Return int from method
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.ireturn
 * @todo plati nejen pro int, ale boolean, byte, char, short - hura doimplementovat
 * @author Adam Vesecky
 */
public class IReturnInstruction {

    public static void run(JVMStack stack) throws LoadingException{
        SIntable value = stack.getCurrentFrame().operandStack.pop();
        JVMLogger.log(JVMLogger.TAG_INSTR, "IReturn: "+value);
        stack.removeCurrentFrame();
        stack.getCurrentFrame().operandStack.push(value);
    }
}

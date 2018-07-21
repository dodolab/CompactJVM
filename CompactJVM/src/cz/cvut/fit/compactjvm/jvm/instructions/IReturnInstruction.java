package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.JVMStack;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;

/**
 * Return int from method
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.ireturn
 * @todo plati nejen pro int, ale boolean, byte, char, short - hura doimplementovat
 * @author Nick Nemame
 */
public class IReturnInstruction {

    public static void run(JVMStack stack) throws LoadingException{
        SIntable value = stack.getCurrentFrame().operandStack.pop();
        JVMLogger.log(JVMLogger.TAG_INSTR, "IReturn: "+value);
        stack.removeCurrentFrame();
        stack.getCurrentFrame().operandStack.push(value);
    }
}

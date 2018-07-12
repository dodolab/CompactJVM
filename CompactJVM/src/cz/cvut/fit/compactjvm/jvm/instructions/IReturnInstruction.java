/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    /**
     * @param stack
     */
    public static void run(JVMStack stack) throws LoadingException{
        SInt value = stack.getCurrentFrame().operandStack.pop();
        JVMLogger.log(JVMLogger.TAG_INSTR, "IReturn: "+value);
        stack.removeCurrentFrame();
        stack.getCurrentFrame().operandStack.push(value);
    }

}

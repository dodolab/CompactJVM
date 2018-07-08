/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.jvm.JVMStack;

/**
 * Return void from method. The interpreter then returns control to the invoker
 * of the method, reinstating the frame of the invoker.
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.return
 * @author Nick Nemame
 */
public class ReturnInstruction {
    
    /**
     * @param stack
     */
    public static void run(JVMStack stack) {
        stack.removeCurrentFrame();
    }

}

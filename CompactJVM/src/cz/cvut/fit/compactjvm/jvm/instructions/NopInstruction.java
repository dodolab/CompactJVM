/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.logging.JVMLogger;

/**
 * perform no operation
 * @author Adam Vesecky
 */
public class NopInstruction {
    
    public static void run(StackFrame stackFrame) {

        JVMLogger.log(JVMLogger.TAG_INSTR, "Nop; doing nothing useful");
    }
    
}
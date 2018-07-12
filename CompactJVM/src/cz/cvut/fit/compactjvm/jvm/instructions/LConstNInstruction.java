/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;

/**
 * push the long #value onto the stack
 * @author Adam Vesecky
 */
public class LConstNInstruction {
    
    public static void run(StackFrame stackFrame, int value) throws LoadingException{
        JVMLogger.log(JVMLogger.TAG_INSTR, "LConst: "+value);
        
        // push long
        stackFrame.operandStack.push(new SLong(value));
    }
}

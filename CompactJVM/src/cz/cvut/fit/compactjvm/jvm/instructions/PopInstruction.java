/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.OperandStack;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.logging.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;

/**
 * perform no operation
 * @author Adam Vesecky
 */
public class PopInstruction {
    
    public static void run(StackFrame stackFrame) throws LoadingException{

        stackFrame.operandStack.pop();
        
        JVMLogger.log(JVMLogger.TAG_INSTR, "Pop; Pop from stack");
    }
    
}

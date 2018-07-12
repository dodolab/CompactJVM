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
 *  // push 0.0f, 1.0f or 2.0f on the stack
 * @author Adam Vesecky
 */
public class FConstInstruction {
    
    public static void run(StackFrame stackFrame, float value) throws LoadingException{
        JVMLogger.log(JVMLogger.TAG_INSTR, "FConst: "+value);
        stackFrame.operandStack.push(new SFloat(value));
    }

    
}

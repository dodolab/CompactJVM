/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.jvm.MethodArea;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.logging.JVMLogger;

/**
 * @author Nick Nemame
 */
public class IStoreNInstruction {
    
    /**
     * 
     * @param stackFrame 
     * @param localVariableIndex 
     */
    public static void run(StackFrame stackFrame, int localVariableIndex) {
        int value = stackFrame.operandStack.popInt();
        JVMLogger.log(JVMLogger.TAG_INSTR, "IStoreN: "+value);
        stackFrame.localVariables.setInt(localVariableIndex, value);
    }

}

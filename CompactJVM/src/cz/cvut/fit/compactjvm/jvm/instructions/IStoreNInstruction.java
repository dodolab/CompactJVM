/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.MethodArea;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;

/**
 *  store int value into variable #index
 * @author Nick Nemame
 */
public class IStoreNInstruction {
    
    /**
     * 
     * @param stackFrame 
     * @param localVariableIndex 
     */
    public static void run(StackFrame stackFrame, int localVariableIndex) throws LoadingException{
        SIntable value = stackFrame.operandStack.pop(); //Int nebo char

        JVMLogger.log(JVMLogger.TAG_INSTR, "IStoreN: "+value);
        stackFrame.localVariables.setVar(localVariableIndex, value);
    }

}

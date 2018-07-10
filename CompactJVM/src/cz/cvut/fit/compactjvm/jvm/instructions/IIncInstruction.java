/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.logging.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;

/**
 * increment local variable #index by signed byte const
 * @author Adam Vesecky
 */
public class IIncInstruction {
    
    public static void run(StackFrame stackFrame) throws LoadingException{
        int index = stackFrame.loadInstructionSingleParam();
        int constant = stackFrame.loadInstructionSingleParam();
        
        SInt localVar = stackFrame.localVariables.getVar(index);
        stackFrame.localVariables.setVar(index, new SInt(localVar.getValue()+constant));
        
        JVMLogger.log(JVMLogger.TAG_INSTR, "IInc: "+localVar+"+="+constant);
    }

    
}

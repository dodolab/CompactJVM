/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.logging.JVMLogger;

/**
 * increment local variable #index by signed byte const
 * @author Adam Vesecky
 */
public class IIncInstruction {
    
    public static void run(StackFrame stackFrame) {
        int index = stackFrame.loadInstructionSingleParam();
        int constant = stackFrame.loadInstructionSingleParam();
        
        int localVar = stackFrame.localVariables.getInt(index);
        stackFrame.localVariables.setInt(index, localVar+constant);
        
        JVMLogger.log(JVMLogger.TAG_INSTR, "IInc: "+localVar+"+="+constant);
    }

    
}

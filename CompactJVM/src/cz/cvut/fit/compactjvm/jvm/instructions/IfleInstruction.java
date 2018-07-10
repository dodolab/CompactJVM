/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.logging.JVMLogger;

/**
 * if value is less than or equal to 0, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)
 * @author Adam Vesecky
 */
public class IfleInstruction {
    
    public static void run(StackFrame stackFrame) {

        // two bytes
        stackFrame.loadInstructionSingleParam();
        byte nextInstruction = stackFrame.loadInstructionSingleParam();
        
        int value = stackFrame.operandStack.popInt();
        
        if(value <= 0){
            JVMLogger.log(JVMLogger.TAG_INSTR, "Ifle: "+value+" <= 0; goto "+nextInstruction);
            stackFrame.setCurrentInstructionIndex(stackFrame.getCurrentInstructionIndex() + nextInstruction - 3);
        }else{
            JVMLogger.log(JVMLogger.TAG_INSTR, "Ifle: "+value+" > 0");
        }
    }
    
}
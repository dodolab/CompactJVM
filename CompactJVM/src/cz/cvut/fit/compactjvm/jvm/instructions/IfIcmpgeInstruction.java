/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.logging.JVMLogger;

/**
 *  if value1 is greater than or equal to value2, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)
 * @author Adam Vesecky
 */
public class IfIcmpgeInstruction {
    
    public static void run(StackFrame stackFrame) {

        // two bytes
        stackFrame.loadInstructionSingleParam();
        byte nextInstruction = stackFrame.loadInstructionSingleParam();
        
        int value2 = stackFrame.operandStack.popInt();
        int value1 = stackFrame.operandStack.popInt();
        
        if(value1 >= value2){
            JVMLogger.log(JVMLogger.TAG_INSTR, "IfIcmpge: "+value1+" >= "+value2+"; goto "+nextInstruction);
            stackFrame.setCurrentInstructionIndex(stackFrame.getCurrentInstructionIndex() + nextInstruction - 3);
        }else{
             JVMLogger.log(JVMLogger.TAG_INSTR, "IfIcmpge: "+value1+" < "+value2);
        }
    }
    
}

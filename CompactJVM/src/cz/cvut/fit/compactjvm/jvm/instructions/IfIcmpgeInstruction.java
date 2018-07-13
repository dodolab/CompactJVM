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
 *  if value1 is greater than or equal to value2, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)
 * @author Adam Vesecky
 */
public class IfIcmpgeInstruction {
    
    public static void run(StackFrame stackFrame) throws LoadingException{

        // two bytes
        stackFrame.loadInstructionSingleParam();
        byte nextInstruction = stackFrame.loadInstructionSingleParam();
        
        SIntable value2 = stackFrame.operandStack.pop();
        SIntable value1 = stackFrame.operandStack.pop();
        
        SInt val1 = value1.toInt();
        SInt val2 = value2.toInt();
        
        if(val1.getValue() >= val2.getValue()){
            JVMLogger.log(JVMLogger.TAG_INSTR, "IfIcmpge: "+value1+" >= "+value2+"; goto "+nextInstruction);
            stackFrame.setCurrentInstructionIndex(stackFrame.getCurrentInstructionIndex() + nextInstruction - 3);
        }else{
             JVMLogger.log(JVMLogger.TAG_INSTR, "IfIcmpge: "+value1+" < "+value2);
        }
    }
    
}

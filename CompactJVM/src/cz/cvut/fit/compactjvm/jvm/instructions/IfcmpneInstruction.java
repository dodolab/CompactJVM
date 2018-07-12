/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.structures.SInt;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;

/**
 *  if value1 is not equal to value2, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)
 * @author Adam Vesecky
 */
public class IfcmpneInstruction {
    
    public static void run(StackFrame stackFrame) throws LoadingException{

        // two bytes
        stackFrame.loadInstructionSingleParam();
        byte nextInstruction = stackFrame.loadInstructionSingleParam();
        
        SInt value2 = stackFrame.operandStack.pop();
        SInt value1 = stackFrame.operandStack.pop();
        
        if(value1.getValue() != value2.getValue()){
            JVMLogger.log(JVMLogger.TAG_INSTR, "IfIcmpne: "+value1+" != "+value2+"; goto "+nextInstruction);
            stackFrame.setCurrentInstructionIndex(stackFrame.getCurrentInstructionIndex() + nextInstruction - 3);
        }else{
             JVMLogger.log(JVMLogger.TAG_INSTR, "IfIcmpne: "+value1+" == "+value2);
        }
    }
    
}

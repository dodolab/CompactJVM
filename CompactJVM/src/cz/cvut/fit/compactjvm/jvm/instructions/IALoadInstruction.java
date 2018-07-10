/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.jvm.StackFrame;
import static cz.cvut.fit.compactjvm.jvm.instructions.ALoadInstruction.run;
import cz.cvut.fit.compactjvm.logging.JVMLogger;

/**
 * load an int from an array
 * @author Adam Vesecky
 */
public class IALoadInstruction {

    public static void run(StackFrame stackFrame){
        
        int index = stackFrame.operandStack.popInt();
        int arrayRef = stackFrame.operandStack.popInt();
        int[] array = stackFrame.arrayStorage.getArray(arrayRef);
        
        int value = array[index];
        stackFrame.operandStack.pushInt(value);
        
        JVMLogger.log(JVMLogger.TAG_INSTR, "IALoad: array["+index+"] : "+value);
    }
}

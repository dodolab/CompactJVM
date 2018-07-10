/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.ObjectHeap;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import static cz.cvut.fit.compactjvm.jvm.instructions.ALoadInstruction.run;
import cz.cvut.fit.compactjvm.logging.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;

/**
 * load an int from an array
 * @author Adam Vesecky
 */
public class IALoadInstruction {

    public static void run(StackFrame stackFrame, ObjectHeap heap) throws LoadingException{
        
        SInt index = stackFrame.operandStack.pop();
        SArrayRef arrayRef = stackFrame.operandStack.pop();
        
        int value = heap.readFromHeap(arrayRef.getReference(), index.getValue());
        stackFrame.operandStack.push(new SInt(value));
        
        JVMLogger.log(JVMLogger.TAG_INSTR, "IALoad: array["+index+"] : "+value);
    }
}

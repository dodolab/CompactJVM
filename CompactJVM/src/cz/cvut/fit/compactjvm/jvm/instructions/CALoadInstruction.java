/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.ArrayOutOfBoundsException;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.ObjectHeap;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import static cz.cvut.fit.compactjvm.jvm.instructions.ALoadInstruction.run;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;

/**
 * load an char from an array
 * @author Adam Vesecky
 */
public class CALoadInstruction {

    public static void run(StackFrame stackFrame, ObjectHeap heap) throws LoadingException, ArrayOutOfBoundsException{
        
        SInt index = stackFrame.operandStack.pop();
        SArrayRef arrayRef = stackFrame.operandStack.pop();
        
        if(index.getValue() >= arrayRef.getSize()) throw new ArrayOutOfBoundsException("Maximum index is "+(arrayRef.getSize() - 1)+", "+index.getValue()+" given.");
        
        SChar value = heap.readFromHeap(arrayRef.getReference(), index.getValue());
        stackFrame.operandStack.push(value);
        
        JVMLogger.log(JVMLogger.TAG_INSTR, "CALoad: array["+index+"] : "+value);
    }
}

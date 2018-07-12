/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.exceptions.OutOfHeapMemException;
import cz.cvut.fit.compactjvm.jvm.ObjectHeap;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;

/**
 * Nacte velikost pole, ktere je dano referenci v zasobniku. Do zasobniku
 * nasledne ulozime tuto velikost pole.
 * @author Nick Nemame
 */
public class ArrayLengthInstruction {

    public static void run(StackFrame stackFrame) throws LoadingException {

        int arrayType = stackFrame.getNextInstruction();

        SArrayRef arrayRef = stackFrame.operandStack.pop();
        SInt arraySize = new SInt(arrayRef.getSize());
        stackFrame.operandStack.push(arraySize);

        JVMLogger.log(JVMLogger.TAG_INSTR, "Get array length: ref: " + arrayRef.getReference() + "; size: "+arrayRef.getSize());
    }
    
}

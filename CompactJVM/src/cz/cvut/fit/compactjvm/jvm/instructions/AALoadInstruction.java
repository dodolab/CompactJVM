/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.jvm.ObjectHeap;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import static cz.cvut.fit.compactjvm.jvm.instructions.ALoadInstruction.run;
import cz.cvut.fit.compactjvm.structures.SArrayRef;
import cz.cvut.fit.compactjvm.structures.SGenericRef;
import cz.cvut.fit.compactjvm.structures.SInt;
import cz.cvut.fit.compactjvm.structures.SObjectRef;

/**
 * Loads reference from array (0x32)
 * 
 * @author Adam Vesecky
 */
public class AALoadInstruction {
    
    public static void run(StackFrame stackFrame, ObjectHeap heap) throws LoadingException{
        // for instruction ALOAD where index is specified by parameter
        SInt index = stackFrame.operandStack.pop();
        SArrayRef arrayRef = stackFrame.operandStack.pop();
        SObjectRef[] arr = heap.readObjectArrayFromHeap(arrayRef.getReference());
        stackFrame.operandStack.push(arr[index.getValue()]);
        
        JVMLogger.log(JVMLogger.TAG_INSTR, "AALoad: object array: "+arrayRef.getArrayType().className+"["+index.getValue()+"] -> "+arr[index.getValue()]);
    }
}

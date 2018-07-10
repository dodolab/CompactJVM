/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.core.Word;
import cz.cvut.fit.compactjvm.entities.FLEntity;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.JVMStack;
import cz.cvut.fit.compactjvm.jvm.ObjectHeap;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.logging.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;

/**
 * Set field in object
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.getfield
 * @author Nick Nemame
 */
public class GetfieldInstruction {
    
    public static final int PARAM_COUNT = 2;
    
    public static void run(JVMStack stack, ObjectHeap heap) throws LoadingException {
        StackFrame stackFrame = stack.getCurrentFrame();
        byte[] bytes = stackFrame.loadInstructionParams(PARAM_COUNT);
        int cpIndex = Word.fromByteArray(bytes);
        
        FLEntity fieldInfo = stackFrame.associatedClass.getFieldInfoByCpIndex(cpIndex);
        
        SObjectRef reference = stackFrame.operandStack.pop();
        int value = heap.readFromHeap(reference.getReference(), fieldInfo.dataFieldOffset);
        //@todo zalezi na typu, nejen int
        stackFrame.operandStack.push(new SInt(value));
        
        JVMLogger.log(JVMLogger.TAG_INSTR, "Get field from heap (reference: "+reference+", value: "+value+")");
    }

}

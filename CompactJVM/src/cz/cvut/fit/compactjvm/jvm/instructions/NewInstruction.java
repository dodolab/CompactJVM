/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.core.Word;
import cz.cvut.fit.compactjvm.entities.CPClass;
import cz.cvut.fit.compactjvm.entities.CPUtf8;
import cz.cvut.fit.compactjvm.exceptions.OutOfHeapMemException;
import cz.cvut.fit.compactjvm.jvm.JVMStack;
import cz.cvut.fit.compactjvm.jvm.MethodArea;
import cz.cvut.fit.compactjvm.jvm.ObjectHeap;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.logging.JVMLogger;

/**
 * creates a new instance of class specified by two next bytes
 * @author Adam Vesecky
 */
public class NewInstruction {

    public static void run(JVMStack stack, MethodArea methodArea, ObjectHeap heap) throws OutOfHeapMemException{
        StackFrame stackFrame = stack.getCurrentFrame();
        byte[] bytes = stackFrame.loadInstructionParams(2);
        int cpIndex = Word.fromByteArray(bytes);
        int classNameIndex = ((CPClass) stackFrame.associatedClass.cpEntities[cpIndex]).nameIndex;
        String className = ((CPUtf8) stackFrame.associatedClass.cpEntities[classNameIndex]).value;
        
        int objectReference = heap.allocObject(methodArea.getClassFile(className));
        
        stackFrame.operandStack.pushReference(objectReference);
        
        JVMLogger.log(JVMLogger.TAG_INSTR, "New class: "+className);
        
    }
}

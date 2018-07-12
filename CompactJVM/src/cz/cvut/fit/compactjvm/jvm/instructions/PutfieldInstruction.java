/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.classfile.Word;
import cz.cvut.fit.compactjvm.cpentities.CPClass;
import cz.cvut.fit.compactjvm.cpentities.CPEntity;
import cz.cvut.fit.compactjvm.cpentities.CPFieldRef;
import cz.cvut.fit.compactjvm.cpentities.CPUtf8;
import cz.cvut.fit.compactjvm.classfile.FLEntity;
import cz.cvut.fit.compactjvm.classfile.NameDesc;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.JVMStack;
import cz.cvut.fit.compactjvm.jvm.MethodArea;
import cz.cvut.fit.compactjvm.jvm.ObjectHeap;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import static cz.cvut.fit.compactjvm.jvm.instructions.InvokeSpecialInstruction.PARAM_COUNT;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;

/**
 * Set field in object
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.putfield
 * @author Nick Nemame
 */
public class PutfieldInstruction {
    
    public static final int PARAM_COUNT = 2;
    
    public static void run(JVMStack stack, ObjectHeap heap, MethodArea methodArea) throws LoadingException {
        StackFrame stackFrame = stack.getCurrentFrame();
        byte[] bytes = stackFrame.loadInstructionParams(PARAM_COUNT);
        int cpIndex = Word.fromByteArray(bytes);
        
        SStruct value = stackFrame.operandStack.pop();
        SObjectRef reference = stackFrame.operandStack.pop();
        
        NameDesc nd = stackFrame.associatedClass.getNameAndDescriptorByCpIndex(cpIndex);
        FLEntity fieldInfo = reference.getClassFile().getFieldInfo(nd.name, nd.descriptor, cpIndex);
        
        
        
        heap.writeToHeap(reference.getReference(), fieldInfo.dataFieldOffset, value);
        JVMLogger.log(JVMLogger.TAG_INSTR, "Put field to heap: (reference: "+reference+", value: "+value+")");
    }

}

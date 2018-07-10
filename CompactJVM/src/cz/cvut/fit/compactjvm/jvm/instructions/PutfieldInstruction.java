/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.core.Word;
import cz.cvut.fit.compactjvm.entities.CPClass;
import cz.cvut.fit.compactjvm.entities.CPFieldRef;
import cz.cvut.fit.compactjvm.entities.CPUtf8;
import cz.cvut.fit.compactjvm.entities.FLEntity;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.JVMStack;
import cz.cvut.fit.compactjvm.jvm.MethodArea;
import cz.cvut.fit.compactjvm.jvm.ObjectHeap;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import static cz.cvut.fit.compactjvm.jvm.instructions.InvokeSpecialInstruction.PARAM_COUNT;
import cz.cvut.fit.compactjvm.logging.JVMLogger;

/**
 * Set field in object
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.putfield
 * @author Nick Nemame
 */
public class PutfieldInstruction {
    
    public static final int PARAM_COUNT = 2;
    
    public static void run(JVMStack stack, ObjectHeap heap) throws LoadingException {
        StackFrame stackFrame = stack.getCurrentFrame();
        byte[] bytes = stackFrame.loadInstructionParams(PARAM_COUNT);
        int cpIndex = Word.fromByteArray(bytes);
        
        //int fieldIndex = ((CPFieldRef) stackFrame.associatedClass.cpEntities[cpIndex]).nameAndTypeIndex;
        FLEntity fieldInfo = stackFrame.associatedClass.getFieldInfoByCpIndex(cpIndex);
        
        //@todo zalezi na typu
        int value = stackFrame.operandStack.popInt();
        int reference = stackFrame.operandStack.popReference();
        
        heap.writeToHeap(reference, fieldInfo.dataFieldOffset, value);
        //int value = stackFrame.localVariables.getInt(localVariableIndex);
        //JVMLogger.log(JVMLogger.TAG_INSTR, "ILoadN: "+value);
        //stackFrame.operandStack.pushInt(value);
    }

}

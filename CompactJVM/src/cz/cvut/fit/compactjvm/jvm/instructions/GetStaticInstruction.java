/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.classfile.ClassFile;
import cz.cvut.fit.compactjvm.classfile.FieldDefinition;
import cz.cvut.fit.compactjvm.cpentities.CPEntity;
import cz.cvut.fit.compactjvm.cpentities.CPFieldRef;
import cz.cvut.fit.compactjvm.classfile.FLEntity;
import cz.cvut.fit.compactjvm.classfile.Word;
import cz.cvut.fit.compactjvm.cpentities.CPNameAndType;
import cz.cvut.fit.compactjvm.cpentities.CPUtf8;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.JVMStack;
import cz.cvut.fit.compactjvm.jvm.MethodArea;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import static cz.cvut.fit.compactjvm.jvm.instructions.InvokeStaticInstruction.loadArgumentsToLocalVariables;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.structures.SStruct;


/**
 * @author Nick Nemame
 */
public class GetStaticInstruction {
    
    /**
     * 
     * @param stackFrame 
     */
    public static void run(JVMStack stack, MethodArea methodArea) throws LoadingException {
        
        StackFrame current = stack.getCurrentFrame();
        
        byte[] data = current.loadInstructionParams(2);
        int index = Word.fromByteArray(data);
        
        FieldDefinition field = current.associatedClass.getFieldDefinition(index);
        String fieldClass = field.getFieldClass();
        ClassFile cls = methodArea.getClassFile(fieldClass);
        cls.constructClass(stack, methodArea);
        
        
        current.operandStack.push(field.getValue());
        JVMLogger.log(JVMLogger.TAG_INSTR, "GetStatic :" +field.getValue());
        
    }

}

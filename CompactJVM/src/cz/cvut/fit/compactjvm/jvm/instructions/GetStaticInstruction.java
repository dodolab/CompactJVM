/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.core.ClassFile;
import cz.cvut.fit.compactjvm.core.FieldDefinition;
import cz.cvut.fit.compactjvm.entities.CPEntity;
import cz.cvut.fit.compactjvm.entities.CPFieldRef;
import cz.cvut.fit.compactjvm.entities.FLEntity;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.JVMStack;
import cz.cvut.fit.compactjvm.jvm.MethodArea;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import static cz.cvut.fit.compactjvm.jvm.instructions.InvokeStaticInstruction.loadArgumentsToLocalVariables;

/**
 * @author Nick Nemame
 */
public class GetStaticInstruction {
    
    /**
     * 
     * @param stackFrame 
     */
    public static void run(JVMStack stack, MethodArea methodArea) throws LoadingException {
        byte localVariableIndex = stack.getCurrentFrame().loadInstructionSingleParam();
 
        FieldDefinition fieldDef = stack.getCurrentFrame().associatedClass.getFieldDefinition(localVariableIndex);
        ClassFile cls = methodArea.getClassFile(fieldDef.getFieldClass());
        int fieldIndex = cls.getFieldIndex(fieldDef.getFieldName(), fieldDef.getFieldDescriptor());
        FLEntity field = cls.getField(fieldIndex);
        
        System.out.println("GetStatic");
        
        // todo ...
    }

}

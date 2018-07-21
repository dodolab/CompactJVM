package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.classfile.ClassFile;
import cz.cvut.fit.compactjvm.classfile.FieldDefinition;
import cz.cvut.fit.compactjvm.parsing.WordParser;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.JVMStack;
import cz.cvut.fit.compactjvm.jvm.MethodArea;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;


/**
 * @author Nick Nemame
 */
public class GetStaticInstruction {
    
    /**
     * Gets static field from class
     * @param stackFrame 
     */
    public static void run(JVMStack stack, MethodArea methodArea) throws LoadingException, Exception {
        
        StackFrame current = stack.getCurrentFrame();
        
        byte[] data = current.loadInstructionParams(2);
        int index = WordParser.fromByteArray(data);
        
        FieldDefinition field = current.associatedClass.getFieldDefinition(index);
        String fieldClass = field.getFieldClass();
        ClassFile cls = methodArea.getClassFile(fieldClass);
        cls.constructClass(stack, methodArea);
        
        
        current.operandStack.push(field.getValue());
        JVMLogger.log(JVMLogger.TAG_INSTR_LOAD, "GetStatic :" +field.getValue());
        
    }

}

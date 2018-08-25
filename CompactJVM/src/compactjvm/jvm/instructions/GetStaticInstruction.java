package compactjvm.jvm.instructions;

import compactjvm.classfile.ClassFile;
import compactjvm.classfile.FieldDefinition;
import compactjvm.parsing.WordParser;
import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.JVMStack;
import compactjvm.jvm.MethodArea;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;


/**
 * @author Adam Vesecky
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

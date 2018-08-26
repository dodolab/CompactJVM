package compactjvm.jvm.instructions;

import compactjvm.classfile.ClassFile;
import compactjvm.classfile.MethodDefinition;
import compactjvm.parsing.WordParser;
import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.JVMStack;
import compactjvm.jvm.MethodArea;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import java.io.IOException;

/**
 * Invoke instance method; special handling for superclass, private, and instance initialization method invocations
 * @author Adam Vesecky
 */
public class InvokeSpecialInstruction {
    
    public static final int PARAM_COUNT = 2;

    public static void run(JVMStack stack, MethodArea methodArea) throws LoadingException, IOException {
        byte[] bytes = stack.getCurrentFrame().loadInstructionParams(PARAM_COUNT);
        // index in CP of a class that invoked that method
        int methodRefIndex = WordParser.fromByteArray(bytes);
        MethodDefinition method = stack.getCurrentFrame().associatedClass.getMethodDefinition(methodRefIndex, 
                stack.getCurrentFrame().associatedMethod, methodArea);
        
        ClassFile classFile = methodArea.getClassFile(method.getMethodClass());
        int methodIndex = classFile.getMethodDefIndex(method.getMethodName(), method.getMethodDescriptor());
        StackFrame frame = new StackFrame(classFile, methodIndex, method, stack.jvmThread);
        loadArgumentsToLocalVariables(stack.getCurrentFrame(), frame, method);
        stack.push(frame);
        
        JVMLogger.log(JVMLogger.TAG_INSTR_INVOKE, "InvokeStatic: "+method.getMethodName());
    }
    

    public static void loadArgumentsToLocalVariables(StackFrame currentFrame, StackFrame newFrame, MethodDefinition method) throws LoadingException {
       
        JVMLogger.log(JVMLogger.TAG_INSTR, "LoadArgumentsToLocalVariables");
        
        int locIndex = method.getMethodParamsWordsCount() + 1;

        for(int i = method.getMethodParams().size() - 1; i >= 0; --i) {
            switch(method.getMethodParams().get(i)) {                
                case "Z": //boolean
                    locIndex -= WordParser.BOOLEAN_WORDS;
                    break;
                case "B": //byte
                    locIndex -= WordParser.BYTE_WORDS;
                    break;
                case "C": //char
                    locIndex -= WordParser.CHAR_WORDS;
                    break;
                case "S": //short
                    locIndex -= WordParser.SHORT_WORDS;
                    break;
                case "I": //int
                    locIndex -= WordParser.INT_WORDS;
                    break;
                case "J": //long
                    locIndex -= WordParser.LONG_WORDS;
                    break;
                case "F": //float
                    locIndex -= WordParser.FLOAT_WORDS;
                    break;
                case "D": //double
                    locIndex -= WordParser.DOUBLE_WORDS;
                    break;
                default: //array, class, ...
                    locIndex -= WordParser.REFERENCE_WORDS;
                    break;
            }
            
            newFrame.localVariables.setVar(locIndex, currentFrame.operandStack.pop());
        }
        // set the reference to the object at 0 index of the local variable array
        newFrame.localVariables.setVar(0, currentFrame.operandStack.pop());
    }

}

package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.classfile.ClassFile;
import cz.cvut.fit.compactjvm.classfile.MethodDefinition;
import cz.cvut.fit.compactjvm.parsing.WordParser;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.JVMStack;
import cz.cvut.fit.compactjvm.jvm.MethodArea;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import java.io.IOException;

/**
 * Invoke instalce method; special handling for superlcass, private, and instance initialization method invocations
 * @author Nick Nemame
 */
public class InvokeSpecialInstruction {
    
    public static final int PARAM_COUNT = 2;

    public static void run(JVMStack stack, MethodArea methodArea) throws LoadingException, IOException {
        byte[] bytes = stack.getCurrentFrame().loadInstructionParams(PARAM_COUNT);
        int methodRefIndex = WordParser.fromByteArray(bytes); //index v CP ve tride, ktera invokuje, nikoliv v te, na ktere je metoda volana
        MethodDefinition method = stack.getCurrentFrame().associatedClass.getMethodDefinition(methodRefIndex, 
                stack.getCurrentFrame().associatedMethod, methodArea);
        
        ClassFile classFile = methodArea.getClassFile(method.getMethodClass());
        int methodIndex = classFile.getMethodDefIndex(method.getMethodName(), method.getMethodDescriptor());
        StackFrame frame = new StackFrame(classFile, methodIndex, method, stack.jvmThread);
        loadArgumentsToLocalVariables(stack.getCurrentFrame(), frame, method);
        stack.push(frame);
        
        JVMLogger.log(JVMLogger.TAG_INSTR, "InvokeStatic: "+method.getMethodName());
    }
    
    /**
     * Nactu ze zasobniku
     * @param currentFrame
     * @param newFrame
     * @param method
     * @throws cz.cvut.fit.compactjvm.exceptions.LoadingException
     */
    public static void loadArgumentsToLocalVariables(StackFrame currentFrame, StackFrame newFrame, MethodDefinition method) throws LoadingException {
       
        JVMLogger.log(JVMLogger.TAG_INSTR, "LoadArgumentsToLocalVariables");
        
        int locIndex = method.getMethodParamsWordsCount() + 1;
        //Kdyz od locIndex odectu pocet slov vkladaneho argumentu, pak dostanu index,
        //na ktery mam do lokalnich promennych argument vlozit
        for(int i = method.getMethodParams().size() - 1; i >= 0; --i) {
            switch(method.getMethodParams().get(i)) {
                // local variable is set once below (powerful generics will distinct types)
                
                case "Z": //boolean
                    locIndex -= WordParser.BOOLEAN_WORDS;
                    break;
                case "B": //byte
                    locIndex -= WordParser.BYTE_WORDS;
                    //newFrame.localVariables.setByte(locIndex, currentFrame.operandStack.popByte());
                    break;
                case "C": //char
                    locIndex -= WordParser.CHAR_WORDS;
                    //newFrame.localVariables.setChar(locIndex, currentFrame.operandStack.popChar());
                    break;
                case "S": //short
                    locIndex -= WordParser.SHORT_WORDS;
                    //newFrame.localVariables.setShort(locIndex, currentFrame.operandStack.popShort());
                    break;
                case "I": //int
                    locIndex -= WordParser.INT_WORDS;
                    //newFrame.localVariables.setInt(locIndex, currentFrame.operandStack.popInt());
                    break;
                case "J": //long
                    locIndex -= WordParser.LONG_WORDS;
                    //newFrame.localVariables.setLong(locIndex, currentFrame.operandStack.popLong());
                    break;
                case "F": //float
                    locIndex -= WordParser.FLOAT_WORDS;
                    //newFrame.localVariables.setFloat(locIndex, currentFrame.operandStack.popFloat());
                    break;
                case "D": //double
                    locIndex -= WordParser.DOUBLE_WORDS;
                    //newFrame.localVariables.setDouble(locIndex, currentFrame.operandStack.popDouble());
                    break;
                default: //array, class, ...
                    locIndex -= WordParser.REFERENCE_WORDS;
                    //newFrame.localVariables.setInt(locIndex, currentFrame.operandStack.popInt());
                    break;
            }
            
            newFrame.localVariables.setVar(locIndex, currentFrame.operandStack.pop());
        }
        //nastavi na pozici 0 lokalnich promennych referenci na objekt
        newFrame.localVariables.setVar(0, currentFrame.operandStack.pop());
    }

}

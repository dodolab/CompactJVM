package compactjvm.jvm.instructions;

import compactjvm.classfile.ClassFile;
import compactjvm.classfile.MethodDefinition;
import compactjvm.parsing.WordParser;
import compactjvm.exceptions.LoadingException;
import compactjvm.exceptions.OutOfHeapMemException;
import compactjvm.jvm.JVMStack;
import compactjvm.jvm.MethodArea;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;


/**
 * Invokes static instruction
 * @author Adam Vesecky
 */
public class InvokeStaticInstruction {

    public static final int PARAM_COUNT = 2;

    public static void run(JVMStack stack, MethodArea methodArea) throws LoadingException, OutOfHeapMemException, Exception {
        byte[] bytes = stack.getCurrentFrame().loadInstructionParams(PARAM_COUNT);
        int methodRefIndex = WordParser.fromByteArray(bytes); //index v CP ve tride, ktera invokuje, nikoliv v te, na ktere je metoda volana
        MethodDefinition method = stack.getCurrentFrame().associatedClass.getMethodDefinition(methodRefIndex,
                stack.getCurrentFrame().associatedMethod, methodArea);

        ClassFile classFile = methodArea.getClassFile(method.getMethodClass());
        
        if (method.isNativeMethod()) {
          //  stack.jvmThread.getHeap().getGarbageCollector().runGC();
            JVMLogger.log(JVMLogger.TAG_INSTR_INVOKE, "Native - InvokeStatic: " + method.getMethodName());
            // call native method
            stack.jvmThread.getNativeArea().callStaticMethod(classFile.getClassName(), method.getMethodName(), stack, method.getMethodParams().size());
        } else {

            int methodIndex = classFile.getMethodDefIndex(method.getMethodName(), method.getMethodDescriptor());

            // construct class
            classFile.constructClass(stack, methodArea);

            StackFrame frame = new StackFrame(classFile, methodIndex, method, stack.jvmThread);
            loadArgumentsToLocalVariables(stack.getCurrentFrame(), frame, method);
            stack.push(frame);

            JVMLogger.log(JVMLogger.TAG_INSTR_INVOKE, "InvokeStatic: " + method.getMethodName());
        }
    }

    /**
     * Nactu ze zasobniku
     *
     * @param currentFrame
     * @param newFrame
     * @param method
     */
    public static void loadArgumentsToLocalVariables(StackFrame currentFrame, StackFrame newFrame, MethodDefinition method) throws LoadingException {

        JVMLogger.log(JVMLogger.TAG_INSTR, "LoadArgumentsToLocalVariables");

        int locIndex = method.getMethodParamsWordsCount();
        //Kdyz od locIndex odectu pocet slov vkladaneho argumentu, pak dostanu index,
        //na ktery mam do lokalnich promennych argument vlozit
        for (int i = method.getMethodParams().size() - 1; i >= 0; --i) {
            switch (method.getMethodParams().get(i)) {
                // local variable is set once below (powerful generics will distinct types)

                case "Z": //boolean
                    locIndex -= WordParser.BOOLEAN_WORDS;
                    //newFrame.localVariables.setBoolean(locIndex, currentFrame.operandStack.popBoolean());
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
    }

}

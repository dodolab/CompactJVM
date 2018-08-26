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


    public static void loadArgumentsToLocalVariables(StackFrame currentFrame, StackFrame newFrame, MethodDefinition method) throws LoadingException {

        JVMLogger.log(JVMLogger.TAG_INSTR, "LoadArgumentsToLocalVariables");

        int locIndex = method.getMethodParamsWordsCount();
        for (int i = method.getMethodParams().size() - 1; i >= 0; --i) {
            switch (method.getMethodParams().get(i)) {

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

            // set the reference to the object at 0 index of the local variable array
            newFrame.localVariables.setVar(locIndex, currentFrame.operandStack.pop());
        }
    }
}

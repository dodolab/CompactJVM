package compactjvm.jvm.instructions;

import compactjvm.classfile.ClassFile;
import compactjvm.classfile.MethodDefinition;
import compactjvm.parsing.WordParser;
import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.JVMStack;
import compactjvm.jvm.MethodArea;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.jvm.ObjectHeap;
import compactjvm.natives.NativeObject;
import compactjvm.structures.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Invokes virtual instruction
 *
 * @author Adam Vesecky
 */
public class InvokeVirtualInstruction {

    public static final int PARAM_COUNT = 2;

    public static void run(JVMStack stack, MethodArea methodArea) throws LoadingException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, Exception {
        byte[] bytes = stack.getCurrentFrame().loadInstructionParams(PARAM_COUNT);
        int methodRefIndex = WordParser.fromByteArray(bytes); //index v CP ve tride, ktera invokuje, nikoliv v te, na ktere je metoda volana
        MethodDefinition method = stack.getCurrentFrame().associatedClass.getMethodDefinition(methodRefIndex,
                stack.getCurrentFrame().associatedMethod, methodArea);

        // following specification, the object we are looking for is BEFORE all parameters
        int params = method.getMethodParams().size();

        // on the peek there should be a object whose method is called
        SObjectRef objectRef = stack.getCurrentFrame().operandStack.get(params);
        
        ClassFile classFile = objectRef.getClassFile();
        
        if(objectRef.isNull()){
            // nullpointer exception
            AAAException.throwException(new NullPointerException(), stack, stack.jvmThread.getHeap(), methodArea);
            return;
        }
        

        if (method.isNativeMethod()) {

            if (!objectRef.hasNativeObject()) {
                throw new LoadingException("Native object expected but not found");
            }

            NativeObject nativeObj = objectRef.getNativeObject();

            // use reflection to call native object method
            Method methodToInvoke = nativeObj.getClass().getMethod(method.getMethodName(), JVMStack.class, ObjectHeap.class);

            try {
                methodToInvoke.invoke(nativeObj, stack, stack.jvmThread.getHeap());
            } catch (Exception e) {

                // propagate exception inside
                AAAException.throwException(e, stack, stack.jvmThread.getHeap(), methodArea);

            }
            JVMLogger.log(JVMLogger.TAG_INSTR_INVOKE, "InvokeVirtual native: " + method.getMethodName());
        } else {
            //Lookup metody v rodicovskych tridach, pokud neni nalezena v aktualni tride
            int methodIndex;
            while ((methodIndex = classFile.getMethodDefIndex(method.getMethodName(), method.getMethodDescriptor())) == -1) {
                if (classFile.getSuperclassName() == null) {
                    throw new LoadingException("Invoke virtual lookup failed - method " + method.getMethodName() + " not found; ClassFile: " + classFile.className);
                }
                classFile = classFile.getSuperClass();
            }

            StackFrame frame = new StackFrame(classFile, methodIndex, method, stack.jvmThread);
            loadArgumentsToLocalVariables(stack.getCurrentFrame(), frame, method);
            stack.push(frame);

            JVMLogger.log(JVMLogger.TAG_INSTR_INVOKE, "InvokeVirtual: " + method.getMethodName());
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

        int locIndex = method.getMethodParamsWordsCount() + 1; // +1 kvuli tomu, ze 1. prvek je objectRef
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
        //nastavi na pozici 0 lokalnich promennych referenci na objekt
        newFrame.localVariables.setVar(0, currentFrame.operandStack.pop());
    }

}

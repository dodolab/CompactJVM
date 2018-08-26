package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 * Loads a size of an array as determined in the reference of the stack
 * 
 * @author Adam Vesecky
 */
public class ArrayLengthInstruction {

    public static void run(StackFrame stackFrame) throws LoadingException {

        SArrayRef arrayRef = stackFrame.operandStack.pop();
        SInt arraySize = new SInt(arrayRef.getSize());
        stackFrame.operandStack.push(arraySize);

        JVMLogger.log(JVMLogger.TAG_INSTR, "ArrayLength: ref: " + arrayRef.getReference() + "; size: "+arrayRef.getSize());
    }
}

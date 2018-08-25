package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 * Nacte velikost pole, ktere je dano referenci v zasobniku. Do zasobniku
 * nasledne ulozime tuto velikost pole.
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

package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;

/**
 * Nacte velikost pole, ktere je dano referenci v zasobniku. Do zasobniku
 * nasledne ulozime tuto velikost pole.
 * @author Nick Nemame
 */
public class ArrayLengthInstruction {

    public static void run(StackFrame stackFrame) throws LoadingException {

        SArrayRef arrayRef = stackFrame.operandStack.pop();
        SInt arraySize = new SInt(arrayRef.getSize());
        stackFrame.operandStack.push(arraySize);

        JVMLogger.log(JVMLogger.TAG_INSTR, "ArrayLength: ref: " + arrayRef.getReference() + "; size: "+arrayRef.getSize());
    }
}

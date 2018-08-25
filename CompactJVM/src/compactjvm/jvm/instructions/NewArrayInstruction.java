package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.exceptions.OutOfHeapMemException;
import compactjvm.jvm.ObjectHeap;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 * create a new array of references of length count and component type
 * identified by the class reference index (indexbyte1 << 8 + indexbyte2) in the
 * constant pool @author Adam Vesecky
 */
public class NewArrayInstruction {

    public static void run(StackFrame stackFrame, ObjectHeap heap) throws LoadingException, ClassNotFoundException, OutOfHeapMemException {

        int arrayType = stackFrame.getNextInstruction();

        SInt size = stackFrame.operandStack.pop();

        if (size.getValue() < 0) {
            throw new LoadingException("Array size cant' be lower than 0");
        }
     
        SStruct[] array;
        
        // primitive array has to be initialized 
        switch (arrayType) {
            case 4: // boolean
                array = new SBoolean[size.getValue()];
                for(int i=0; i<array.length; i++) array[i] = new SBoolean(false);
                break;
            case 5: // char
                array = new SChar[size.getValue()];
                for(int i=0; i<array.length; i++) array[i] = new SChar('\u0000'); // null character
                break;
            case 6: // float
                array = new SFloat[size.getValue()];
                for(int i=0; i<array.length; i++) array[i] = new SFloat(0);
                break;
            case 7: // double
                array = new SDouble[size.getValue()];
                for(int i=0; i<array.length; i++) array[i] = new SDouble(0);
                break;
            case 8: // byte
                array = new SByte[size.getValue()];
                for(int i=0; i<array.length; i++) array[i] = new SByte((byte)0);
                break;
            case 9: // short
                array = new SShort[size.getValue()];
                for(int i=0; i<array.length; i++) array[i] = new SShort((short)0);
                break;
            case 10: // int
                array = new SInt[size.getValue()];
                for(int i=0; i<array.length; i++) array[i] = new SInt(0);
                break;
            case 11: // long
                array = new SLong[size.getValue()];
                for(int i=0; i<array.length; i++) array[i] = new SLong(0);
                break;
            default:
                throw new LoadingException("Unknown array type: "+arrayType);
        }

        SArrayRef arrayReference = heap.allocPrimitiveArray(array, size.getValue());

        stackFrame.operandStack.push(arrayReference);

        JVMLogger.log(JVMLogger.TAG_INSTR, "NewArray: size " + size + "; type: "+array.getClass().getName());
    }
    
}

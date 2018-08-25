package compactjvm.jvm.instructions;
import compactjvm.jvm.JVMLogger;
import compactjvm.jvm.StackFrame;

/**
 * Set static field in class
 * @author Adam Vesecky
 */
public class PutStaticInstruction {
    
    public static final int PARAM_COUNT = 2;
    
    public static void run(StackFrame stackFrame) {
        //byte[] fieldRefIndexBytes = stackFrame.loadInstructionParams(PARAM_COUNT);
        //int fieldRefIndex = 
        //int value = stackFrame.operandStack.popInt();
        JVMLogger.log(JVMLogger.TAG_INSTR_STORE, "!!!!!!!!!!!!!!!!!! PutStatic: TODO");
        //stackFrame.operandStack.pushInt(value);
    }
    
}

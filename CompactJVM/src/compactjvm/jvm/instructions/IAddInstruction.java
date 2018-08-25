package compactjvm.jvm.instructions;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.structures.*;

/**
 * Add int
 * The values are popped from the operand stack.
 * The int result is value1 + value2. The result is pushed onto the operand stack.
 * Despite the fact that overflow may occur, execution of an iadd instruction never throws a run-time exception.
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.iadd
 * @author Adam Vesecky
 */
public class IAddInstruction {
    
    public static void run(StackFrame stackFrame)throws LoadingException {
        SInt operand1 = stackFrame.operandStack.pop();
        SInt operand2 = stackFrame.operandStack.pop();
        
        JVMLogger.log(JVMLogger.TAG_INSTR, "IAdd: "+operand1+" + "+operand2);
        
        SStruct value = new SInt(operand1.getValue() + operand2.getValue());
        stackFrame.operandStack.push(value);
    }

}

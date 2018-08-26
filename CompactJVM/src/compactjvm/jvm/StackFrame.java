package compactjvm.jvm;

import compactjvm.classfile.ClassFile;
import compactjvm.classfile.MethodDefinition;
import compactjvm.attributes.AttrCode;
import compactjvm.exceptions.LoadingException;

/**
 * Stack frame
 *
 * @author Adam Vesecky
 */
public class StackFrame {

    // local variables
    public LocalVariableArray localVariables;
    // operands
    public OperandStack operandStack; //ukladaji se zde operandy, instrukce
    // the class this method is called on
    public final ClassFile associatedClass;
    // index of the method inside a class file
    public final int associatedMethod;
    // instructions for executing this method
    private AttrCode codeAttribute;
    // index inside an array of instructions
    private int currentInstructionIndex;
    // definition of the method (class name, method name, descriptor)
    public MethodDefinition methodDefinition = null;

    public JVMThread jvmThread;

    /**
     * Creates a new stack frame
     *
     */
    public StackFrame(ClassFile classFile, int invokedMethod, MethodDefinition methodDefinition, JVMThread jvmThread) throws LoadingException {
        this.jvmThread = jvmThread;
        associatedClass = classFile;
        associatedMethod = invokedMethod;
        initializeFrame();
        this.methodDefinition = methodDefinition;
    }

    /**
     * Initializes a frame - loads a list of all instruction from invoked method
     */
    private void initializeFrame() throws LoadingException {
        codeAttribute = associatedClass.getMethod(associatedMethod).getCodeAttribute();
        int localVariablesCount = codeAttribute.maxLocals;
        localVariables = new LocalVariableArray(localVariablesCount);
        operandStack = new OperandStack();
        currentInstructionIndex = 0;
    }

    public boolean hasMoreInstructions() {
        return currentInstructionIndex < codeAttribute.codeLength;
    }

    /**
     * Gets a code of the next instruction
     */
    public byte getNextInstruction() {
        return readNextCodeByte();
    }

    /**
     * Loads parameters of currently loaded instruction - the instruction itself
     * determines how many parameters it requires
     *
     * @param paramsCount number of required parameters
     * @return parameters in an array. Their order is the same as in the
     * bytecode
     */
    public byte[] loadInstructionParams(int paramsCount) {
        byte[] params = new byte[paramsCount];
        for (int i = 0; i < paramsCount; ++i) {
            params[i] = readNextCodeByte();
        }
        return params;
    }

    /**
     * Loads goto jump
     *
     * @return
     */
    public int loadInstructionJumpAddr() {
        byte[] bytes = loadInstructionParams(2);
        int val = (bytes[0] << 8) | bytes[1];

        int nextInstruction = this.currentInstructionIndex + val - 3;
        if (nextInstruction < 0) {
            nextInstruction = 256 + nextInstruction;
        }

        return nextInstruction;
    }

    /**
     * Loads one parameter
     *
     * @return
     */
    public byte loadInstructionSingleParam() {
        return readNextCodeByte();
    }

    /**
     * Gets current instruction index
     */
    public int getCurrentInstructionIndex() {
        return currentInstructionIndex;
    }

    /**
     * Sets new instruction index; this method is used e.g. by GOTO instruction
     * or in IF-ELSE branch hop
     *
     * @param val
     */
    public void setCurrentInstructionIndex(int val) {

        if (val < 0) {
            val = 256 + val;
            JVMLogger.log(JVMLogger.TAG_INSTR_JUMP, "Correcture in jump: " + val);
        }

        currentInstructionIndex = val;
    }

    /**
     * Gets complete code in byte array
     *
     * @return
     */
    public byte[] getCode() {
        return codeAttribute.code;
    }

    /**
     * Loads the next byte from the code and moves the index
     *
     */
    private byte readNextCodeByte() {
        byte nextByte = codeAttribute.code[currentInstructionIndex];
        currentInstructionIndex++;
        return nextByte;
    }

}

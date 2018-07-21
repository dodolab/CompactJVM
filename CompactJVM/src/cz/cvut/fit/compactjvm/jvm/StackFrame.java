package cz.cvut.fit.compactjvm.jvm;

import cz.cvut.fit.compactjvm.classfile.ClassFile;
import cz.cvut.fit.compactjvm.classfile.MethodDefinition;
import cz.cvut.fit.compactjvm.attributes.AttrCode;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;

/**
 * Stack frame
 * @author Nick Nemame
 */
public class StackFrame {
    
    public LocalVariableArray localVariables; //obsahuje lokalni promenne
    public OperandStack operandStack; //ukladaji se zde operandy, instrukce
    public final ClassFile associatedClass; //trida, na niz se tato metoda vola
    public final int associatedMethod; //index metody v ClassFile
    private AttrCode codeAttribute; //obsahuje instrukce pro vykonani metody
    private int currentInstructionIndex; //index v poli instrukci
    public MethodDefinition methodDefinition = null; //Definice metody - nazev tridy, nazev metody, rozparsovany descriptor

    public JVMThread jvmThread;
    
    /**
     * Creates a new stack frame 
     * @param classFile
     * @param invokedMethod
     * @param methodDefinition
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
     * Inicializuje frame
     * - nacte seznam instrukci z volane metody
     * - inicializuje pole lokalnich promennych a zasobnik operandu
     */
    private void initializeFrame() throws LoadingException {
        codeAttribute = associatedClass.getMethod(associatedMethod).getCodeAttribute();
        int localVariablesCount = codeAttribute.maxLocals;
        localVariables = new LocalVariableArray(localVariablesCount);
        operandStack = new OperandStack();
        /* @todo - budu muset nacitat kod metody podle dedicnosti, muze to byt kod metody ze superclass atd. */
        currentInstructionIndex = 0;
    }
    
    /**
     * Zjisti, zda metoda jeste obsahuje dalsi instrukce
     * @return 
     */
    public boolean hasMoreInstructions() {
        return currentInstructionIndex < codeAttribute.codeLength;
    }
    
    /**
     * Ziska kod dalsi instrukce
     * @return 
     */
    public byte getNextInstruction() {
        return readNextCodeByte();
    }
    
    /**
     * Nacte parametry nactene instrukce - instrukce si rekne, kolik bytovych
     * parametru vyzaduje, ale sama si do codeAttribute nesaha
     * @param paramsCount Pozadovany pocet parametru
     * @return parametry v poli podle toho, jak sli za sebou v bytecode
     */
    public byte[] loadInstructionParams(int paramsCount) {
        byte[] params = new byte[paramsCount];
        for(int i = 0; i < paramsCount; ++i) {
            params[i] = readNextCodeByte();
        }
        return params;
    }
    
    /**
     * Nacte prave jeden parametr, pokud jej instrukce vyzaduje.
     * @return 
     */
    public byte loadInstructionSingleParam() {
        return readNextCodeByte();
    }
    
    /**
     * Gets current instruction index
     */
    public int getCurrentInstructionIndex(){
        return currentInstructionIndex;
    }
    
    /**
     * Sets new instruction index; this method is used e.g. 
     * by GOTO instruction or in IF-ELSE branch hop
     * @param val 
     */
    public void setCurrentInstructionIndex(int val){
        currentInstructionIndex = val;
    }
    
    /**
     * Gets complete code in byte array
     * @return 
     */
    public byte[] getCode(){
        return codeAttribute.code;
    }
    
    /**
     * Nacte dalsi byte z kodu a posune index, vyuziva se pri nacteni instrukci
     * a jejich parametru z bytecodu
     * @return 
     */
    private byte readNextCodeByte() {
        byte nextByte = codeAttribute.code[currentInstructionIndex];
        currentInstructionIndex++;
        return nextByte;
    }
    
}

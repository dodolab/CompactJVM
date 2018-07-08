/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm;

import cz.cvut.fit.compactjvm.core.ClassFile;
import cz.cvut.fit.compactjvm.core.MethodDefinition;
import cz.cvut.fit.compactjvm.entities.AttrCode;

/**
 *
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
    /** @todo tohle by melo byt spis v pcRegistru u JVM threadu, ale jak se pak dostanu
     k mistu, kde jsem byl v predchozim framu pred vyvolanim metody? */
    
    
    /**
     * @param classFile
     * @param invokedMethod
     * @param methodDefinition
     * 
     */
    public StackFrame(ClassFile classFile, int invokedMethod, MethodDefinition methodDefinition) {
        this(classFile, invokedMethod);
        this.methodDefinition = methodDefinition;
    }
    
    /**
     * @param classFile
     * @param invokedMethod
     */
    public StackFrame(ClassFile classFile, int invokedMethod) {
        associatedClass = classFile;
        associatedMethod = invokedMethod;
        initializeFrame();
    }
    
    /**
     * Inicializuje frame
     * - nacte seznam instrukci z volane metody
     * - inicializuje pole lokalnich promennych a zasobnik operandu
     */
    private void initializeFrame() {
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

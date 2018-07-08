/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm;

import cz.cvut.fit.compactjvm.core.ClassFile;
import cz.cvut.fit.compactjvm.entities.AttrCode;
import java.util.Stack;

/**
 *
 * @author Nick Nemame
 */
public class StackFrame {
    
    public LocalVariableArray localVariables; //obsahuje lokalni promenne (double a long zabiraji 2 byty, horni byte by nemel byt dostupny naprimo
    public Stack<Byte> operandStack;
    private final ClassFile associatedClass;
    private final int associatedMethod; //index metody v ClassFile
    private AttrCode codeAttribute;
    
    public StackFrame(ClassFile classFile, int invokedMethod) {
        associatedClass = classFile;
        associatedMethod = invokedMethod;
        initializeFrame();
    }
    
    private void initializeFrame() {
        codeAttribute = associatedClass.getMethod(associatedMethod).getCodeAttribute();
        int localVariablesCount = codeAttribute.maxLocals;
        localVariables = new LocalVariableArray(localVariablesCount);
    }
    
}

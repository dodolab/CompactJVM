/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm;

import cz.cvut.fit.compactjvm.core.ClassFile;
import cz.cvut.fit.compactjvm.entities.MTHEntity;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.instructions.InstructionManager;

/**
 *
 * JVM Thread je vlakno v ramci jedne instance JVM stroje.
 * Oddeleno od samotneho JVM pro pripad, ze bychom chteli implementovat vlakna.
 * 
 * Kazdy JVM Thread obdrzi sdilenou "method area" vytvori vlastni soukromy JVM Stack
 *
 * Kazdy JVM Thread dale obdrzi nazev tridy a metody, ktera definuje zacatek behu
 * programu daneho vlakna.
 * 
 * @author Nick Nemame
 */
public class JVMThread {
    
    private final InstructionManager instructionManager; //spousti pozadovane instrukce, poskytuje instrukcim potrebne datove zdroje
    private final JVMStack jvmStack;
    private final MethodArea methodArea; //sdilena s ostatnimi JVM vlakny
    
    //private StackFrame currentFrame;
    //@todo pc register misto currentFrame?
    
    public JVMThread(MethodArea methodArea) {
        jvmStack = new JVMStack();
        this.methodArea = methodArea;
        instructionManager = new InstructionManager(jvmStack, methodArea);
    }
    
    /**
     * Spusti beh programu v tomto threadu
     * @todo nacitani trid je treba dodelat, mozna se parametry zmeni
     * @param className
     * @param methodName 
     */
    public void run(String className) throws LoadingException {
        
        // get main method
        ClassFile classFile = methodArea.getClassFile(className);
        int mainMethodIndex = classFile.getMethodIndex("main", "()V");
        StackFrame currentFrame = new StackFrame(classFile, mainMethodIndex);
        jvmStack.push(currentFrame);
        
        while(!jvmStack.isEmpty() && jvmStack.getCurrentFrame().hasMoreInstructions()) {
            instructionManager.runInstruction(jvmStack.getCurrentFrame().getNextInstruction());
        }
    }

    
}

package cz.cvut.fit.compactjvm.jvm;

import cz.cvut.fit.compactjvm.classfile.ClassFile;
import cz.cvut.fit.compactjvm.classfile.MethodDefinition;
import cz.cvut.fit.compactjvm.exceptions.ArrayOutOfBoundsException;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.exceptions.OutOfHeapMemException;
import cz.cvut.fit.compactjvm.jvm.instructions.InstructionManager;
import cz.cvut.fit.compactjvm.structures.SArrayRef;
import cz.cvut.fit.compactjvm.structures.SObjectRef;
import java.io.IOException;

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
    private final ObjectHeap heap; // shared with others threads

    private NativeArea nativeArea;
    
    public JVMThread(MethodArea methodArea, ObjectHeap heap) {
        jvmStack = new JVMStack(this);
        this.methodArea = methodArea;
        instructionManager = new InstructionManager(jvmStack, methodArea, heap);
        this.heap = heap;
        heap.setJVMThread(this);

        nativeArea = new NativeArea(methodArea,heap);
    }
    
    public NativeArea getNativeArea(){
        return nativeArea;
    }
    
    public JVMStack getStack(){
        return jvmStack;
    }
    
    public InstructionManager getInstructionManager(){
        return instructionManager;
    }
    
    /**
     * Spusti beh programu v tomto threadu
     */
    public void run() throws LoadingException, ClassNotFoundException, OutOfHeapMemException, ArrayOutOfBoundsException, Exception {
        
        String className = methodArea.getMainClass();
        // get main method
        ClassFile classFile = methodArea.getClassFile(className);
        int mainMethodDefIndex = classFile.getMethodDefIndex("main", "([Ljava/lang/String;)V");
        MethodDefinition def = classFile.getMethodDefinition(mainMethodDefIndex, methodArea, className, "main", "()V");
        StackFrame currentFrame = new StackFrame(classFile, mainMethodDefIndex, def, this);
        initializeArguments(currentFrame);
        jvmStack.push(currentFrame);
        
        while(!jvmStack.isEmpty() && jvmStack.getCurrentFrame().hasMoreInstructions()) {
            instructionManager.runInstruction(jvmStack.getCurrentFrame().getNextInstruction());
        }
       
    }

    /**
     * Zapise do haldy vsechny stringy a pole techto stringu.
     * Referenci na toto pole ulozi do prvniho StackFrame do prvni lokalni promenne
     * @param frame
     * @throws OutOfHeapMemException
     * @throws IOException 
     */
    public void initializeArguments(StackFrame frame) throws OutOfHeapMemException, IOException, LoadingException {
        //vlozim stringy do heapy
        String[] args = methodArea.getArgs();
        SObjectRef[] argsRef = new SObjectRef[args.length];
        for(int i = 0; i < args.length; ++i) {
            argsRef[i] = nativeArea.writeStringToHeap(args[i]);
        }
        //vlozim pole referenci na stringy do heapy
        SArrayRef argArrayRef = heap.allocObjectArray(methodArea.getClassFile("java/lang/String"), argsRef.length);
        for(int i = 0; i < args.length; ++i) {
            heap.writeToHeap(argArrayRef.getReference(), i, argsRef[i]);
        }
        //ulozim referenci na pole stringu do prvni lokalni promenne
        frame.localVariables.setVar(0, argArrayRef);
    }
    
    public ObjectHeap getHeap(){
        return this.heap;
    }
    
}

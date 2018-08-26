package compactjvm.jvm;

import compactjvm.classfile.ClassFile;
import compactjvm.classfile.MethodDefinition;
import compactjvm.exceptions.ArrayOutOfBoundsException;
import compactjvm.exceptions.LoadingException;
import compactjvm.exceptions.OutOfHeapMemException;
import compactjvm.jvm.instructions.InstructionManager;
import compactjvm.structures.SArrayRef;
import compactjvm.structures.SObjectRef;
import java.io.IOException;

/**
 * A Single JVM thread
 * 
 * Every thread receives a shared "methdo area" and creates their own private JVM Stack
 * Every thread also receives a name of the class and method, defining the beginning of the code of given thread
 * 
 * Multithreading is not supported at the moment (Why should I bother anyway??!)
 * 
 * @author Adam Vesecky
 */
public class JVMThread {
    
    // manager that executes instructions, providing them with necessary sources
    private final InstructionManager instructionManager; 
    private final JVMStack jvmStack;
    private final MethodArea methodArea; // shared with other threads
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
     * Executes the application in this thread
     */
    public void run() throws LoadingException, ClassNotFoundException, OutOfHeapMemException, ArrayOutOfBoundsException, Exception {
        
        String className = methodArea.getMainClass();
        // get main method
        ClassFile classFile = methodArea.getClassFile(className);
        int mainMethodDefIndex = classFile.getMethodDefIndex("main", "([Ljava/lang/String;)V"); // main method descriptor. What a magic!
        MethodDefinition def = classFile.getMethodDefinition(mainMethodDefIndex, methodArea, className, "main", "()V");
        StackFrame currentFrame = new StackFrame(classFile, mainMethodDefIndex, def, this);
        initializeArguments(currentFrame);
        jvmStack.push(currentFrame);
        
        while(!jvmStack.isEmpty() && jvmStack.getCurrentFrame().hasMoreInstructions()) {
            instructionManager.runInstruction(jvmStack.getCurrentFrame().getNextInstruction());
        }       
    }

    /**
     * Writes all strings and their arrays into the heap
     * Stores the reference for this array into the first StackFrame and the first local variable
     * 
     */
    public void initializeArguments(StackFrame frame) throws OutOfHeapMemException, IOException, LoadingException {
        //put strings into heap
        String[] args = methodArea.getArgs();
        SObjectRef[] argsRef = new SObjectRef[args.length];
        for(int i = 0; i < args.length; ++i) {
            argsRef[i] = nativeArea.writeStringToHeap(args[i]);
        }
        // put the string array reference into heap
        SArrayRef argArrayRef = heap.allocObjectArray(methodArea.getClassFile("java/lang/String"), argsRef.length);
        for(int i = 0; i < args.length; ++i) {
            heap.writeToHeap(argArrayRef.getReference(), i, argsRef[i]);
        }
        // save the reference into the first local variable
        frame.localVariables.setVar(0, argArrayRef);
    }
    
    public ObjectHeap getHeap(){
        return this.heap;
    }
}

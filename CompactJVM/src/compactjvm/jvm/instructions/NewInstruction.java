package compactjvm.jvm.instructions;

import compactjvm.classfile.ClassFile;
import compactjvm.parsing.WordParser;
import compactjvm.cpentities.CPClass;
import compactjvm.cpentities.CPUtf8;
import compactjvm.exceptions.LoadingException;
import compactjvm.exceptions.OutOfHeapMemException;
import compactjvm.jvm.JVMStack;
import compactjvm.jvm.MethodArea;
import compactjvm.jvm.ObjectHeap;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.natives.NativeObject;
import compactjvm.structures.*;
import java.io.IOException;

/**
 * creates a new instance of class specified by two next bytes
 * @author Adam Vesecky
 */
public class NewInstruction {

    public static void run(JVMStack stack, MethodArea methodArea, ObjectHeap heap) throws OutOfHeapMemException, LoadingException, IOException{
        StackFrame stackFrame = stack.getCurrentFrame();
        byte[] bytes = stackFrame.loadInstructionParams(2);
        int cpIndex = WordParser.fromByteArray(bytes);
        int classNameIndex = ((CPClass) stackFrame.associatedClass.cpEntities[cpIndex]).nameIndex;
        String className = ((CPUtf8) stackFrame.associatedClass.cpEntities[classNameIndex]).value;
        
        ClassFile cls = methodArea.getClassFile(className);
        SObjectRef objectReference = heap.allocObject(cls);
        
        if(cls.hasNativeMethods()){
            NativeObject obj = stack.jvmThread.getNativeArea().createNativeObject(cls.className);
            objectReference.setNativeObject(obj);
            JVMLogger.log(JVMLogger.TAG_OTHER, " created native object for "+className);
        }
        
        stackFrame.operandStack.push(objectReference);
        JVMLogger.log(JVMLogger.TAG_INSTR, "New class: "+className);  
    }
}

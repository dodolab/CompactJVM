package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.classfile.ClassFile;
import cz.cvut.fit.compactjvm.parsing.WordParser;
import cz.cvut.fit.compactjvm.cpentities.CPClass;
import cz.cvut.fit.compactjvm.cpentities.CPUtf8;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.exceptions.OutOfHeapMemException;
import cz.cvut.fit.compactjvm.jvm.JVMStack;
import cz.cvut.fit.compactjvm.jvm.MethodArea;
import cz.cvut.fit.compactjvm.jvm.ObjectHeap;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.natives.NativeObject;
import cz.cvut.fit.compactjvm.structures.*;
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

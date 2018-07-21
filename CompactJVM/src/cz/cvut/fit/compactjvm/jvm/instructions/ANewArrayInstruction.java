package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.classfile.ClassFile;
import cz.cvut.fit.compactjvm.parsing.WordParser;
import cz.cvut.fit.compactjvm.cpentities.CPClass;
import cz.cvut.fit.compactjvm.cpentities.CPUtf8;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.exceptions.OutOfHeapMemException;
import cz.cvut.fit.compactjvm.jvm.MethodArea;
import cz.cvut.fit.compactjvm.jvm.ObjectHeap;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.structures.SArrayRef;
import cz.cvut.fit.compactjvm.structures.SInt;
import java.io.IOException;

/**
 * Instruction for allocating object array
 * 
 * @author Adam Vesecky
 */
public class ANewArrayInstruction {
 
    
    public static void run(StackFrame stackFrame, MethodArea methodArea, ObjectHeap heap) throws LoadingException, OutOfHeapMemException, IOException{

        // get class name 
        byte[] bytes = stackFrame.loadInstructionParams(2);
        int cpIndex = WordParser.fromByteArray(bytes);
        int classNameIndex = ((CPClass) stackFrame.associatedClass.cpEntities[cpIndex]).nameIndex;
        String className = ((CPUtf8) stackFrame.associatedClass.cpEntities[classNameIndex]).value;
        ClassFile cls = methodArea.getClassFile(className);
        
        // get array size
        SInt size = stackFrame.operandStack.pop();
        
        if(size.getValue() < 0) throw new LoadingException("Array size is lower than 0 !!");
        
        SArrayRef arrayReference = heap.allocObjectArray(cls, size.getValue());
        stackFrame.operandStack.push(arrayReference);
        
        JVMLogger.log(JVMLogger.TAG_INSTR_STORE, "New object array: "+className);

    } 
}

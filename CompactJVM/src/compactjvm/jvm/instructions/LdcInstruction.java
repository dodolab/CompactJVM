package compactjvm.jvm.instructions;

import compactjvm.cpentities.CPString;
import compactjvm.cpentities.CPFloat;
import compactjvm.cpentities.CPUtf8;
import compactjvm.cpentities.CPInteger;
import compactjvm.cpentities.CPEntity;
import compactjvm.cpentities.CPDouble;
import compactjvm.definitions.ConstantPoolType;
import compactjvm.exceptions.LoadingException;
import compactjvm.exceptions.OutOfHeapMemException;
import compactjvm.jvm.ObjectHeap;
import compactjvm.jvm.StackFrame;
import compactjvm.jvm.JVMLogger;
import compactjvm.jvm.MethodArea;
import compactjvm.structures.*;
import java.io.IOException;

/**
 * Push item form run-time constant pool
 * 
 * @author Adam Vesecky
 */
public class LdcInstruction {
 
    
     public static void run(StackFrame stackFrame, MethodArea methodArea, ObjectHeap heap) throws LoadingException, OutOfHeapMemException, IOException
     {
         // get constant pool index
         byte cstPoolIndex = stackFrame.loadInstructionSingleParam();
         // get constant pool item
         CPEntity entity = stackFrame.associatedClass.cpEntities[cstPoolIndex];
         
         switch(entity.tag){
             case ConstantPoolType.CPT_Integer:
                 CPInteger integer = (CPInteger)entity;
                 stackFrame.operandStack.push(new SInt(integer.intVal));
                 JVMLogger.log(JVMLogger.TAG_INSTR_LOAD, "Ldc Integer: " + integer.intVal);
                 break;
             case ConstantPoolType.CPT_Float:
                 CPFloat itFloat = (CPFloat)entity;
                 stackFrame.operandStack.push(new SFloat(itFloat.floatVal));
                 JVMLogger.log(JVMLogger.TAG_INSTR_LOAD, "Ldc Float: " + itFloat.floatVal);
                 break;
             case ConstantPoolType.CPT_Double:
                 CPDouble itDouble = (CPDouble)entity;
                 stackFrame.operandStack.push(new SDouble(itDouble.doubleVal));
                 JVMLogger.log(JVMLogger.TAG_INSTR_LOAD, "Ldc Double: " + itDouble.doubleVal);
                 break;
             case ConstantPoolType.CPT_String:
                 CPString itString = (CPString)entity;
                 // get utf8 value based on string index
                 CPUtf8 utf8 = (CPUtf8)stackFrame.associatedClass.cpEntities[itString.stringIndex];
                 String stringText = utf8.value;
                 SObjectRef strRef = stackFrame.jvmThread.getNativeArea().writeStringToHeap(stringText);
                 stackFrame.operandStack.push(strRef);
                 JVMLogger.log(JVMLogger.TAG_INSTR_LOAD, "Ldc String: " + stringText);
                 break;
         } 
     }
}

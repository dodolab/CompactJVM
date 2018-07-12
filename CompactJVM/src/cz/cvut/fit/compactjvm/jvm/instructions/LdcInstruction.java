/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.classfile.ClassFile;
import cz.cvut.fit.compactjvm.cpentities.CPString;
import cz.cvut.fit.compactjvm.cpentities.CPFloat;
import cz.cvut.fit.compactjvm.cpentities.CPUtf8;
import cz.cvut.fit.compactjvm.cpentities.CPInteger;
import cz.cvut.fit.compactjvm.cpentities.CPEntity;
import cz.cvut.fit.compactjvm.cpentities.CPDouble;
import cz.cvut.fit.compactjvm.definitions.ConstantPoolType;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.exceptions.OutOfHeapMemException;
import cz.cvut.fit.compactjvm.jvm.ObjectHeap;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;
import cz.cvut.fit.compactjvm.jvm.MethodArea;
import cz.cvut.fit.compactjvm.structures.*;

/**
 * Push item form run-time constant pool
 * 
 * @author Adam Vesecky
 */
public class LdcInstruction {
 
    
     public static void run(StackFrame stackFrame, MethodArea methodArea, ObjectHeap heap) throws LoadingException, OutOfHeapMemException
     {
         // get constant pool index
         byte cstPoolIndex = stackFrame.loadInstructionSingleParam();
         // get constant pool item
         CPEntity entity = stackFrame.associatedClass.cpEntities[cstPoolIndex];
         
         switch(entity.tag){
             case ConstantPoolType.CPT_Integer:
                 CPInteger integer = (CPInteger)entity;
                 stackFrame.operandStack.push(new SInt(integer.intVal));
                 JVMLogger.log(JVMLogger.TAG_INSTR, "Ldc Integer: " + integer.intVal);
                 break;
             case ConstantPoolType.CPT_Float:
                 CPFloat itFloat = (CPFloat)entity;
                 stackFrame.operandStack.push(new SFloat(itFloat.floatVal));
                 JVMLogger.log(JVMLogger.TAG_INSTR, "Ldc Float: " + itFloat.floatVal);
                 break;
             case ConstantPoolType.CPT_Double:
                 CPDouble itDouble = (CPDouble)entity;
                 stackFrame.operandStack.push(new SDouble(itDouble.doubleVal));
                 JVMLogger.log(JVMLogger.TAG_INSTR, "Ldc Double: " + itDouble.doubleVal);
                 break;
             case ConstantPoolType.CPT_String:
                 CPString itString = (CPString)entity;
                 // get utf8 value based on string index
                 CPUtf8 utf8 = (CPUtf8)stackFrame.associatedClass.cpEntities[itString.stringIndex];
                 String stringText = utf8.value;
                 SObjectRef strRef = writeStringToHeap(methodArea, heap, stringText);
                 stackFrame.operandStack.push(strRef);
                 JVMLogger.log(JVMLogger.TAG_INSTR, "Ldc String: " + stringText);
                 break;
         }

         
     }
     
     private static SObjectRef writeStringToHeap(MethodArea methodArea, ObjectHeap heap, String stringText) throws OutOfHeapMemException {
        char[] stringData = stringText.toCharArray();
        SChar[] charData = new SChar[stringData.length];
        for(int i = 0; i < charData.length; ++i) charData[i] = new SChar(stringData[i]);
        SArrayRef charDataRef = heap.allocPrimitiveArray(charData, charData.length);
        ClassFile cls = methodArea.getClassFile("java/lang/String");
        SObjectRef strDataRef = heap.allocObject(cls);
        heap.writeToHeap(strDataRef.getReference(), 0, charDataRef);
        return strDataRef;
     }
}

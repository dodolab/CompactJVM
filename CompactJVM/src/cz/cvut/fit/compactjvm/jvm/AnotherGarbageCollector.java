/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm;

import cz.cvut.fit.compactjvm.core.ClassFile;
import cz.cvut.fit.compactjvm.entities.FLEntity;
import cz.cvut.fit.compactjvm.logging.JVMLogger;
import cz.cvut.fit.compactjvm.structures.*;
import java.util.ArrayList;
import java.util.Iterator;
/**
 *
 * @author Adam Vesecky
 */
public class AnotherGarbageCollector {

    private ArrayList<Integer> objectsToKeep = new ArrayList<Integer>();
    private ObjectHeap heap;
    private JVMStack stack;
    
    public AnotherGarbageCollector(ObjectHeap heap, JVMStack stack){
        this.heap = heap;
        this.stack = stack;
    }

    public void runGC() {
        objectsToKeep.clear();
        JVMLogger.log(JVMLogger.TAG_GC, "############# Starting GC ############");
        searchInStack(stack);
        swapHeap();
        objectsToKeep.clear();
        JVMLogger.log(JVMLogger.TAG_GC, "############# Ending GC ############");
    }

    // tries to find all living objects
    private void searchInStack(JVMStack stack){
        Iterator<StackFrame> stackFrameIt = stack.getAllFrames().iterator();
        
        JVMLogger.log(JVMLogger.TAG_GC, "Searching in stack");
        
        int counter = 0;
        while(stackFrameIt.hasNext()){
            JVMLogger.increaseGlobalPadding(1);
            StackFrame frame = stackFrameIt.next();
            searchInFrame(frame, counter++);
            JVMLogger.decreaseGlobalPadding(1);
        }
    }
    
    
    private void searchInFrame(StackFrame frame, int index){
        Iterator<SStruct> operands = frame.operandStack.operandStack.iterator();
        
        JVMLogger.log(JVMLogger.TAG_GC, "--Searching in frame "+index);
        
        JVMLogger.log(JVMLogger.TAG_GC, "  --Searching in operands");
        while(operands.hasNext()){
            
            SStruct operand = operands.next();
            searchInStructure(operand);
        }

        
        SStruct[] localVars = frame.localVariables.localVariables;
        
        JVMLogger.log(JVMLogger.TAG_GC, "  --Searching in local variables");
        for(int i=0; i<localVars.length; i++){
            SStruct localVar = localVars[i];
            if(localVar != null){
                searchInStructure(localVar);    
            }
        }

    }
    
    private void searchInStructure(SStruct struct){
        JVMLogger.increaseGlobalPadding(4);
        if(struct.isReference()){
            SGenericRef reference = (SGenericRef) struct;
            if(!reference.isNull()){
                markLivingObject(reference.getReference());
                
                // go deeper
                if(reference instanceof SObjectRef){
                    SObjectRef objRef = (SObjectRef)reference;
                    JVMLogger.log(JVMLogger.TAG_GC, "Found object "+(objRef.getClassFile() == null ? "xx" : objRef.getClassFile().className));
                    searchInObjectReference((SObjectRef)reference);
                }else{
                    SArrayRef arrRef = (SArrayRef)reference;
                    JVMLogger.log(JVMLogger.TAG_GC, "Found array "+(arrRef.getArrayType()== null ? "xx" : arrRef.getArrayType().className));
                    searchInArrayReference((SArrayRef)reference);
                }

            }
        }else{
            // not reference, nothing to do here...
        }
        JVMLogger.decreaseGlobalPadding(4);
    }
    
    private void searchInObjectReference(SObjectRef reference){
        JVMLogger.increaseGlobalPadding(1);
        ClassFile classFile = reference.getClassFile();
        FLEntity[] fieldInfos = classFile.fieldInfos;
        
        for(int i=0; i<fieldInfos.length; i++){
            FLEntity field = fieldInfos[i];
            if(field.isReference()){
                SObjectRef fld = heap.readFromHeap(reference.getReference(), i);
                if(!fld.isNull()){
                    JVMLogger.log(JVMLogger.TAG_GC, "Found field "+field.name+" in "+classFile.className);
                    markLivingObject(fld.getReference());
                    searchInObjectReference(fld);
                }
            }else if(field.isObjectArray()){
                SArrayRef fld = heap.readFromHeap(reference.getReference(), i);
                if(!fld.isNull()){
                    JVMLogger.log(JVMLogger.TAG_GC, "Found object array "+field.name+" in "+classFile.className);
                    markLivingObject(fld.getReference());
                    searchInArrayReference(fld);
                }
            }else if(field.isPrimitiveArray()){
                SArrayRef fld = heap.readFromHeap(reference.getReference(), i);
                if(!fld.isNull()){
                    JVMLogger.log(JVMLogger.TAG_GC, "Found primitive array "+field.name+" in "+classFile.className);
                    markLivingObject(fld.getReference());
                }
            }
        }
        JVMLogger.decreaseGlobalPadding(1);
    }
    
    private void searchInArrayReference(SArrayRef reference){
        JVMLogger.increaseGlobalPadding(1);
        if(!reference.isPrimitive()){
            SObjectRef[] arrayOfReferences = (SObjectRef[]) heap.readObjectArrayFromHeap(reference.getReference());
            
            for(int i=0; i<arrayOfReferences.length; i++){
                SObjectRef fldRef = arrayOfReferences[i];
                if(!fldRef.isNull()){
                    markLivingObject(fldRef.getReference());
                    JVMLogger.log(JVMLogger.TAG_GC, "Found array item "+(fldRef.getClassFile() == null ? "xx" : fldRef.getClassFile().className));
                    searchInObjectReference(fldRef);
                }
            }
        }
        JVMLogger.decreaseGlobalPadding(1);
    }
    
    private void markLivingObject(int reference){
        if(!objectsToKeep.contains(reference)){
            objectsToKeep.add(reference);
        }
    }

    private void swapHeap() {
        heap.swapHeap();
        
        for(Integer livingObj : objectsToKeep){
            heap.moveObjectFromOldHeap(livingObj.intValue());
        }
    }
}

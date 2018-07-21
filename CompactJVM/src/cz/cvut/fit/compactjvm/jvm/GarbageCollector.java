
package cz.cvut.fit.compactjvm.jvm;

import cz.cvut.fit.compactjvm.classfile.ClassFile;
import cz.cvut.fit.compactjvm.classfile.FLEntity;
import cz.cvut.fit.compactjvm.structures.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Simple garbage collector that marks live objects, copies them to the 
 * second part of heap and disposes the first part
 * @author Adam Vesecky
 */
public class GarbageCollector {

    private ArrayList<Integer> objectsToKeep = new ArrayList<Integer>();
    private ObjectHeap heap;
 
    
    public GarbageCollector(ObjectHeap heap){
        this.heap = heap;
    }

    public void runGC() {
        JVMStack stack = CompactJVM.getInstance().getThreads().get(0).getStack();
        
        objectsToKeep.clear();
        JVMLogger.log(JVMLogger.TAG_GC, "############# Starting GC ############");
        
        // search living objects
        searchInStack(stack);
        // swap heap
        swapHeap();
        objectsToKeep.clear();
        JVMLogger.log(JVMLogger.TAG_GC, "############# Ending GC ############");
    }

    // tries to find all living objects in a stack
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
    
    // tries to find all living objects in a stackframe
    private void searchInFrame(StackFrame frame, int index){
        Iterator<SStruct> operands = frame.operandStack.operandStack.iterator();
        
        JVMLogger.log(JVMLogger.TAG_GC, "--Searching in frame "+index);
        JVMLogger.log(JVMLogger.TAG_GC, "  --Searching in operands");
        
        // search in operands
        while(operands.hasNext()){
            SStruct operand = operands.next();
            searchInStructure(operand);
        }

        
        SStruct[] localVars = frame.localVariables.localVariables;
        JVMLogger.log(JVMLogger.TAG_GC, "  --Searching in local variables");
        
        // search in local variables
        for(int i=0; i<localVars.length; i++){
            SStruct localVar = localVars[i];
            if(localVar != null){
                searchInStructure(localVar);    
            }
        }
    }
    
    // tries to find all living objects inside a structure
    private void searchInStructure(SStruct struct){
        JVMLogger.increaseGlobalPadding(4);
        
        if(struct.isReference()){
            // search in reference
            SGenericRef reference = (SGenericRef) struct;
            if(!reference.isNull()){
                // found living object
                markLivingObject(reference.getReference());
                
                // go deeper recursively
                if(reference instanceof SObjectRef){
                    // object is a simple object
                    SObjectRef objRef = (SObjectRef)reference;
                    JVMLogger.log(JVMLogger.TAG_GC, "Found object "+(objRef.getClassFile() == null ? "xx" : objRef.getClassFile().className));
                    searchInObjectReference((SObjectRef)reference);
                }else{
                    // objcet is an array
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
    
    // tries to find all living object inside another object
    private void searchInObjectReference(SObjectRef reference){
        JVMLogger.increaseGlobalPadding(1);
        
        // get class file and all fields
        ClassFile classFile = reference.getClassFile();
        FLEntity[] fieldInfos = classFile.fieldInfos;
        
        // iterate over all fields
        for(int i=0; i<fieldInfos.length; i++){
            FLEntity field = fieldInfos[i];
            if(field.isReference()){
                // found object field
                SObjectRef fld = heap.readFromHeap(reference.getReference(), i);
                if(!fld.isNull()){
                    JVMLogger.log(JVMLogger.TAG_GC, "Found field "+field.name+" in "+classFile.className);
                    // mark and continue in recursion
                    markLivingObject(fld.getReference());
                    searchInObjectReference(fld);
                }
            }else if(field.isObjectArray()){
                // found object array
                SArrayRef fld = heap.readFromHeap(reference.getReference(), i);
                if(!fld.isNull()){
                    JVMLogger.log(JVMLogger.TAG_GC, "Found object array "+field.name+" in "+classFile.className);
                    // mark and continue in recursion
                    markLivingObject(fld.getReference());
                    searchInArrayReference(fld);
                }
            }else if(field.isPrimitiveArray()){
                // found primitive array
                SArrayRef fld = heap.readFromHeap(reference.getReference(), i);
                if(!fld.isNull()){
                    // primitive array doesn't have any references so that recursion ends here
                    JVMLogger.log(JVMLogger.TAG_GC, "Found primitive array "+field.name+" in "+classFile.className);
                    markLivingObject(fld.getReference());
                }
            }
        }
        JVMLogger.decreaseGlobalPadding(1);
    }
    
    // tries to find all living objects inside an array
    private void searchInArrayReference(SArrayRef reference){
        JVMLogger.increaseGlobalPadding(1);
        if(!reference.isPrimitive()){
            SObjectRef[] arrayOfReferences = (SObjectRef[]) heap.readObjectArrayFromHeap(reference.getReference());
            
            // iterate over all references
            for(int i=0; i<arrayOfReferences.length; i++){
                SObjectRef fldRef = arrayOfReferences[i];
                if(!fldRef.isNull()){
                    // mark and continue in recursion
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
        // swap heap
        heap.swapHeap();
        
        // move living object to the other part
        for(Integer livingObj : objectsToKeep){
            heap.moveObjectFromOldHeap(livingObj.intValue());
        }
    }
}

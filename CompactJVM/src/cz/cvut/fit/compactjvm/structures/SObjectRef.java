/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.structures;

import cz.cvut.fit.compactjvm.classfile.ClassFile;
import cz.cvut.fit.compactjvm.natives.NativeObject;

/**
 * Object reference
 * 
 * @author Adam Vesecky
 */
public class SObjectRef extends SGenericRef{
    private ClassFile classFile;
    private NativeObject nativeObject; // assigned native object (if there is one)
    
    public SObjectRef(){
        this.classFile = null;
    }
    
    public SObjectRef(int heapReference){
        super(heapReference); // generic reference
    }
    
    public SObjectRef(int heapReference, ClassFile classFile){
        super(heapReference);
        this.classFile = classFile;
    }
    
    public SObjectRef(int heapReference, ClassFile classFile, NativeObject nativeObject){
        this(heapReference, classFile);
        this.nativeObject = nativeObject;
    }
    
    public ClassFile getClassFile(){
        return classFile;
    }
    
    public void setClassFile(ClassFile cls){
        this.classFile = cls;
    }
    
    public boolean hasNativeObject(){
        return nativeObject != null;
    }
    
    public NativeObject getNativeObject(){
        return nativeObject;
    }
    
    public void setNativeObject(NativeObject obj){
        this.nativeObject = obj;
    }

    @Override
    public SObjectRef makeCopy() {
        return new SObjectRef(heapReference, classFile, nativeObject);
    }
    
    @Override
    public String toString() {
        if(this.isNull()){
            return "SObjectRef[null]";
        }else{
            return "SObjectRef["+(this.classFile == null ? "???" : this.classFile.getClassName())+"]["+this.heapReference+"]";
        }
    }

}

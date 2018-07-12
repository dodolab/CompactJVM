/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.structures;

import cz.cvut.fit.compactjvm.classfile.ClassFile;

/**
 * Object reference
 * 
 * @author Adam Vesecky
 */
public class SObjectRef extends SGenericRef{
    private ClassFile classFile;
    
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
    
    public ClassFile getClassFile(){
        return classFile;
    }
    
    public void setClassFile(ClassFile cls){
        this.classFile = cls;
    }

    @Override
    public SObjectRef makeCopy() {
        return new SObjectRef(heapReference, classFile);
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

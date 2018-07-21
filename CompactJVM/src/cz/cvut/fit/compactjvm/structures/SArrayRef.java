package cz.cvut.fit.compactjvm.structures;

import cz.cvut.fit.compactjvm.classfile.ClassFile;

/**
 * Array reference
 * 
 * @author Adam Vesecky
 */
public class SArrayRef extends SGenericRef {
    protected int size;
    protected ClassFile classFile; // array type
    
    public SArrayRef(int heapReference){
        super(heapReference);
    }
    
    public SArrayRef(int heapReference, ClassFile classFile){
        super(heapReference);
        this.classFile = classFile;
    }
    
    public SArrayRef(int heapReference, ClassFile classFile, int size){
        super(heapReference);
        this.classFile = classFile;
        this.size = size;
    }
    
    public int getSize(){
        return size;
    }
    
    public void setSize(int size){
        this.size = size;
    }
    
    public ClassFile getArrayType(){
        return classFile;
    }
    
    public void setArrayType(ClassFile classFile){
        this.classFile = classFile;
    }
    
    public boolean isPrimitive(){
        return classFile == null;
    }
    
    @Override
    public SArrayRef makeCopy() {
	return new SArrayRef(heapReference,classFile,size);
     }

    @Override
    public String toString() {
        if(this.isNull()){
            return "array[null]";
        }else{
            return "array#"+this.heapReference+"#[sz="+this.size+"]("+(this.classFile == null ? "primitive" : this.classFile.getClassName())+")";
        }
    }
}

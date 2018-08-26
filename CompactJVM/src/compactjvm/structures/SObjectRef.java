package compactjvm.structures;

import compactjvm.classfile.ClassFile;
import compactjvm.natives.NativeObject;

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
            return "object(null)"+"<id:"+this.id+">";
        }else{
            return "object#"+this.heapReference+"#("+(this.classFile == null ? "???" : this.classFile.getClassName())+")"+"<id:"+this.id+">";
        }
    }
}

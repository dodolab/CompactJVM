package compactjvm.natives;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.JVMStack;
import compactjvm.jvm.ObjectHeap;
import compactjvm.structures.SInt;
import compactjvm.structures.SObjectRef;
import compactjvm.structures.SStruct;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Native wrapper for text writer
 * @author Adam Vesecky
 */
public class TextWriter extends NativeObject{
    
    private BufferedWriter writer;
    private String path;
    
    @Override
    public void construct(JVMStack stack, ObjectHeap heap) throws LoadingException, FileNotFoundException, IOException{
        
        SObjectRef stringRef = stack.getCurrentFrame().operandStack.pop();
        String str = stack.jvmThread.getNativeArea().readStringFromHeap(stringRef);
        
        this.path = str;
        writer = new BufferedWriter(new java.io.FileWriter(path));
    }
    
    public void close(JVMStack stack, ObjectHeap heap) throws IOException{
        writer.close();
    }
    
    public void append(JVMStack stack, ObjectHeap heap) throws IOException, LoadingException{
        SStruct ref = stack.getCurrentFrame().operandStack.pop();
        
        if(ref.isReference()){
            // string
            String s = stack.jvmThread.getNativeArea().readStringFromHeap((SObjectRef)ref);
            writer.append(s);
        }else{
            // integer
            int intVal = ((SInt)ref).getValue();
            writer.append(intVal+"");
        }
    }
     
    public void appendLine(JVMStack stack, ObjectHeap heap) throws IOException, LoadingException{
        SObjectRef ref = stack.getCurrentFrame().operandStack.pop();
        String s = stack.jvmThread.getNativeArea().readStringFromHeap(ref);
        writer.append(s);
        writer.newLine();
    }
}

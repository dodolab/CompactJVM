package compactjvm.natives;

import compactjvm.exceptions.LoadingException;
import compactjvm.exceptions.OutOfHeapMemException;
import compactjvm.jvm.JVMStack;
import compactjvm.jvm.ObjectHeap;
import compactjvm.structures.SObjectRef;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Native wrapper for text reader
 * @author Adam Vesecky
 */
public class TextReader extends NativeObject{
    
    private BufferedReader reader;
    private String path;
    
    @Override
    public void construct(JVMStack stack, ObjectHeap heap) throws LoadingException, FileNotFoundException{
        
        SObjectRef stringRef = stack.getCurrentFrame().operandStack.pop();
        String str = stack.jvmThread.getNativeArea().readStringFromHeap(stringRef);
        
        this.path = str;
        reader = new BufferedReader(new java.io.FileReader(path));
    }
    
    public void close(JVMStack stack, ObjectHeap heap) throws IOException{
        reader.close();
    }
    
    public void nextLine(JVMStack stack, ObjectHeap heap) throws IOException, LoadingException, OutOfHeapMemException{
        String nextLine = reader.readLine();
        SObjectRef ref = stack.jvmThread.getNativeArea().writeStringToHeap(nextLine);
        stack.getCurrentFrame().operandStack.push(ref);
    }

}

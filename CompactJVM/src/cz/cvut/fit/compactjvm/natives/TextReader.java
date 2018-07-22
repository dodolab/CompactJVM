package cz.cvut.fit.compactjvm.natives;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.exceptions.OutOfHeapMemException;
import cz.cvut.fit.compactjvm.jvm.JVMStack;
import cz.cvut.fit.compactjvm.jvm.ObjectHeap;
import cz.cvut.fit.compactjvm.structures.SObjectRef;
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
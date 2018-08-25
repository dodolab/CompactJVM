package compactjvm.natives;

import compactjvm.exceptions.LoadingException;
import compactjvm.jvm.JVMStack;
import compactjvm.jvm.ObjectHeap;
import compactjvm.structures.SArrayRef;
import compactjvm.structures.SByte;
import compactjvm.structures.SInt;
import compactjvm.structures.SObjectRef;
import compactjvm.structures.SStruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Native wrapper for file reader
 * @author Adam Vesecky
 */
public class FileReader extends NativeObject{
    
    private FileInputStream stream;
    private String path;
    
    @Override
    public void construct(JVMStack stack, ObjectHeap heap) throws LoadingException, FileNotFoundException{
        
        SObjectRef stringRef = stack.getCurrentFrame().operandStack.pop();
        String str = stack.jvmThread.getNativeArea().readStringFromHeap(stringRef);
        
        this.path = str;
        stream = new FileInputStream(path);
    }
    
    public void available(JVMStack stack, ObjectHeap heap) throws IOException{
        int aval = stream.available();
        stack.getCurrentFrame().operandStack.push(new SInt(aval));
    }
    
    public void close(JVMStack stack, ObjectHeap heap) throws IOException{
        stream.close();
    }
    
    public void read(JVMStack stack, ObjectHeap heap) throws IOException, LoadingException{
        if(stack.getCurrentFrame().operandStack.isEmpty()){
            int read = stream.read();
            stack.getCurrentFrame().operandStack.push(new SInt(read));
        }else{
            // second method -> byte array is referenced
            SArrayRef arrayRef = stack.getCurrentFrame().operandStack.pop();
            SStruct[] arr = heap.readPrimitiveArrayFromHeap(arrayRef.getReference());
            
            for(int i=0; i<arr.length; i++){
                arr[i] = new SByte((byte)stream.read());
                // write back to heap
                heap.writeToHeap(arrayRef.getReference(), i, arr[i]);
            }
            
            stack.getCurrentFrame().operandStack.push(new SInt(0));
        }
    }
    
    public void skip(JVMStack stack) throws LoadingException, IOException{
        SInt howMuch = stack.getCurrentFrame().operandStack.pop();
        int skp = (int)stream.skip((int)howMuch.getValue());
        stack.getCurrentFrame().operandStack.push(new SInt(skp));
    }
}

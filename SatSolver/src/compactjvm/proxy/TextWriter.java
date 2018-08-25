package compactjvm.proxy;

import java.io.IOException;

/**
 * Simple file writer
 * @author Adam Vesecky
 */
public class TextWriter {
    
    public TextWriter(String path){
        construct(path);
    }
    
    public native void construct(String path);
       
    public native void close();
    
    public native void append(String s);
    
    public native void appendLine(String s);
    
    public native void append(int num);
}

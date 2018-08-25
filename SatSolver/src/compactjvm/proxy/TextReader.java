package compactjvm.proxy;

/**
 * Simple text reader
 * 
 * @author Adam Vesecky
 */
public class TextReader {
    
    public TextReader(String path){
       construct(path);
    }
    
    public native void construct(String path);
    
    public native void close();
    
    public native String nextLine();    
}

package compactjvm.proxy;

/**
 * Wrapper for native static functions
 * 
 * @author Adam Vesecky
 */
public class JVMFunctions {
    
    public static native void println(String msg);
    
    public static native void println(int num);
    
    public static native void println(String msg, int num);
    
    public static native int parseInt(String string);
}

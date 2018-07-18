/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compactjvmlab;

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

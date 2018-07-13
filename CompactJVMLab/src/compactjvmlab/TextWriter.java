/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compactjvmlab;

import java.io.IOException;

/**
 * Outstanding file writer
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

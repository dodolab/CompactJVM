/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compactjvmlab;

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

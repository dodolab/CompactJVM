/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.proxies;

import java.io.IOException;

/**
 * Outstanding file reader
 * @author Adam Vesecky
 */
public class FileReader {
    
    public FileReader(String path){
        construct(path);
    }
    
    public native void construct(String path);
    
    public native int available();
    
    public native void close();
    
    public native int read();

    public native int read(byte[] b);
    
    public native int skip(int howMuch);
}

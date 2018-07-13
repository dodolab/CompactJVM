/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.natives;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.exceptions.OutOfHeapMemException;
import cz.cvut.fit.compactjvm.jvm.JVMStack;
import cz.cvut.fit.compactjvm.jvm.ObjectHeap;
import cz.cvut.fit.compactjvm.structures.SArrayRef;
import cz.cvut.fit.compactjvm.structures.SByte;
import cz.cvut.fit.compactjvm.structures.SInt;
import cz.cvut.fit.compactjvm.structures.SObjectRef;
import cz.cvut.fit.compactjvm.structures.SStruct;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Native wrapper for text writer
 * @author Adam Vesecky
 */
public class TextWriter extends NativeObject{
    
    private BufferedWriter writer;
    private String path;
    
    @Override
    public void construct(JVMStack stack, ObjectHeap heap) throws LoadingException, FileNotFoundException, IOException{
        
        SObjectRef stringRef = stack.getCurrentFrame().operandStack.pop();
        String str = stack.jvmThread.getNativeArea().readStringFromHeap(stringRef);
        
        this.path = str;
        writer = new BufferedWriter(new java.io.FileWriter(path));
    }
    
    public void close(JVMStack stack, ObjectHeap heap) throws IOException{
        writer.close();
    }
    
    public void append(JVMStack stack, ObjectHeap heap) throws IOException, LoadingException{
        SStruct ref = stack.getCurrentFrame().operandStack.pop();
        
        if(ref.isReference()){
            // string
            String s = stack.jvmThread.getNativeArea().readStringFromHeap((SObjectRef)ref);
            writer.append(s);
        }else{
            // integer
            int intVal = ((SInt)ref).getValue();
            writer.append(intVal+"");
        }
    }
     
    public void appendLine(JVMStack stack, ObjectHeap heap) throws IOException, LoadingException{
        SObjectRef ref = stack.getCurrentFrame().operandStack.pop();
        String s = stack.jvmThread.getNativeArea().readStringFromHeap(ref);
        writer.append(s);
        writer.newLine();
    }

}

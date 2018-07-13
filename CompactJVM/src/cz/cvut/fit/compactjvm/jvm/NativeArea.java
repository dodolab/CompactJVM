/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm;

import cz.cvut.fit.compactjvm.classfile.ClassFile;
import cz.cvut.fit.compactjvm.classfile.FLEntity;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.exceptions.OutOfHeapMemException;
import cz.cvut.fit.compactjvm.natives.FileReader;
import cz.cvut.fit.compactjvm.natives.NativeObject;
import cz.cvut.fit.compactjvm.structures.SArrayRef;
import cz.cvut.fit.compactjvm.structures.SBoolean;
import cz.cvut.fit.compactjvm.structures.SByte;
import cz.cvut.fit.compactjvm.structures.SChar;
import cz.cvut.fit.compactjvm.structures.SInt;
import cz.cvut.fit.compactjvm.structures.SObjectRef;
import cz.cvut.fit.compactjvm.structures.SStruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Nativky
 *
 * @author Adam Vesecky
 */
public class NativeArea {

    private MethodArea methodArea;
    private ObjectHeap heap;

    public NativeArea(MethodArea methodArea, ObjectHeap heap) {
        this.methodArea = methodArea;
        this.heap = heap;
    }

    public void callMethod(String className, String methodName, JVMStack stack, int numParams) throws LoadingException {

        String rawClassName = className.substring(className.lastIndexOf("/") + 1);
        if (rawClassName.equals("JVMFunctions")) {
            switch (methodName) {
                case "println":
                    jvm_println(stack, numParams);
                    break;
                default:
                    throw new LoadingException("No such native method: " + methodName);
            }
        }else{
            throw new LoadingException("No such native class: " + className);
        }
    }

    public NativeObject createNativeObject(String className) throws LoadingException {
        String rawClassName = className.substring(className.lastIndexOf("/") + 1);

        switch (rawClassName) {
            case "FileReader":
                return new FileReader();
            default:
                throw new LoadingException("Native object for " + className + " not implemented yet!");
        }
    }

    public SObjectRef writeStringToHeap(String stringText) throws OutOfHeapMemException {
        char[] stringData = stringText.toCharArray();
        SChar[] charData = new SChar[stringData.length];
        for (int i = 0; i < charData.length; ++i) {
            charData[i] = new SChar(stringData[i]);
        }
        SArrayRef charDataRef = heap.allocPrimitiveArray(charData, charData.length);
        ClassFile cls = methodArea.getClassFile("java/lang/String");
        SObjectRef strDataRef = heap.allocObject(cls);
        heap.writeToHeap(strDataRef.getReference(), 0, charDataRef);
        return strDataRef;
    }

    public String readStringFromHeap(SObjectRef objectRef) {
        SArrayRef charDataRef = heap.readFromHeap(objectRef.getReference(), 0);
        SStruct[] charData = (SStruct[]) heap.readPrimitiveArrayFromHeap(charDataRef.getReference());

        char[] charArr = new char[charData.length];

        for (int i = 0; i < charData.length; i++) {
            char myChar = ((SChar) charData[i]).getValue();
            charArr[i] = myChar;
        }

        return new String(charArr);
    }

    private void jvm_println(JVMStack stack, int numParams) throws LoadingException {

        ArrayList<String> messages = new ArrayList<String>();

        // append all parameters
        for(int i=0; i<numParams; i++) {
            SStruct struct = stack.getCurrentFrame().operandStack.pop();
            String str;

            if (struct.isReference()) {
                // is string
                str = readStringFromHeap((SObjectRef) struct);
            } else if(struct instanceof SInt){
                // must be integer
                int val = ((SInt) struct).getValue();
                str = val + "";
            }else{
                byte val = ((SByte) struct).getValue();
                str = val + "";
            }
            
            messages.add(str);
        }
        // reverse list of messages 
        Collections.reverse(messages);
        StringBuilder output = new StringBuilder();
        
        for(String s : messages){
            output.append(s + " ");
        }
        
        // parameters are inverted !
        JVMLogger.log(JVMLogger.TAG_PRINT, output.toString());
    }
}

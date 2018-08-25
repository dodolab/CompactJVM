package compactjvm.jvm;

import compactjvm.classfile.ClassFile;
import compactjvm.exceptions.LoadingException;
import compactjvm.exceptions.OutOfHeapMemException;
import compactjvm.natives.FileReader;
import compactjvm.natives.NativeObject;
import compactjvm.natives.TextReader;
import compactjvm.natives.TextWriter;
import compactjvm.structures.SArrayRef;
import compactjvm.structures.SByte;
import compactjvm.structures.SChar;
import compactjvm.structures.SInt;
import compactjvm.structures.SObjectRef;
import compactjvm.structures.SStruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class used for invoking native methods and creating native objects
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

    /**
     * Calls the static native method
     * Method must be from class JVMFunctions, located in CompactJVMLib project
     * @param className name of class
     * @param methodName name of method
     * @param stack
     * @param numParams
     * @throws LoadingException 
     */
    public void callStaticMethod(String className, String methodName, JVMStack stack, int numParams) throws LoadingException {

        // parse static method
        String rawClassName = className.substring(className.lastIndexOf("/") + 1);
        if (rawClassName.equals("JVMFunctions")) {
            switch (methodName) {
                case "println":
                    jvm_println(stack, numParams);
                    break;
                case "parseInt":
                    jvm_parseInt(stack, numParams);
                    break;
                default:
                    throw new LoadingException("No such native method: " + methodName);
            }
        } else {
            throw new LoadingException("No such native class: " + className);
        }
    }

    /**
     * Creates a new native object
     * Native objects are connected with SObjectRef references and can be used
     * to invoke native methods
     * @param className
     * @return
     * @throws LoadingException 
     */
    public NativeObject createNativeObject(String className) throws LoadingException {
        String rawClassName = className.substring(className.lastIndexOf("/") + 1);

        // create object based on class name
        switch (rawClassName) {
            case "FileReader":
                return new FileReader();
            case "TextReader":
                return new TextReader();
            case "TextWriter":
                return new TextWriter();
            default:
                throw new LoadingException("Native object for " + className + " not implemented yet!");
        }
    }

    /**
     * Writes string to the heap as a char array
     * @param stringText
     * @return
     */
    public SObjectRef writeStringToHeap(String stringText) throws OutOfHeapMemException, IOException {
        if (stringText != null) {
            JVMLogger.log(JVMLogger.TAG_HEAP, "Writing string "+stringText);
            char[] stringData = stringText.toCharArray();
            SChar[] charData = new SChar[stringData.length];
            for (int i = 0; i < charData.length; ++i) {
                charData[i] = new SChar(stringData[i]);
            }
            
            JVMLogger.log(JVMLogger.TAG_HEAP, "Allocating char array");
            SArrayRef charDataRef = heap.allocPrimitiveArray(charData, charData.length);
            ClassFile cls = methodArea.getClassFile("java/lang/String");
            JVMLogger.log(JVMLogger.TAG_HEAP, "Allocating string object");
            SObjectRef strDataRef = heap.allocObject(cls);
            JVMLogger.log(JVMLogger.TAG_HEAP, "Connecting array with string");
            heap.writeToHeap(strDataRef.getReference(), 0, charDataRef);
            return strDataRef;
        } else {
            // null;
            return new SObjectRef();
        }
    }

    /**
     * Reads a string from the heap; each string is stored as a char array
     * @param objectRef
     * @return 
     */
    public String readStringFromHeap(SObjectRef objectRef) {
        
        JVMLogger.log(JVMLogger.TAG_HEAP, "Reading string #"+objectRef.getReference()+"#");
        SArrayRef charDataRef = heap.readFromHeap(objectRef.getReference(), 0);
        SStruct[] charData = (SStruct[]) heap.readPrimitiveArrayFromHeap(charDataRef.getReference());

        char[] charArr = new char[charData.length];

        for (int i = 0; i < charData.length; i++) {
            char myChar = ((SChar) charData[i]).getValue();
            charArr[i] = myChar;
        }

        return new String(charArr);
    }

    // println native function
    private void jvm_println(JVMStack stack, int numParams) throws LoadingException {

        ArrayList<String> messages = new ArrayList<String>();

        // append all parameters
        for (int i = 0; i < numParams; i++) {
            SStruct struct = stack.getCurrentFrame().operandStack.pop();
            String str;

            if (struct.isReference()) {
                // is string
                str = readStringFromHeap((SObjectRef) struct);
            } else if (struct instanceof SInt) {
                // must be integer
                int val = ((SInt) struct).getValue();
                str = val + "";
            } else {
                byte val = ((SByte) struct).getValue();
                str = val + "";
            }

            messages.add(str);
        }
        // reverse list of messages 
        Collections.reverse(messages);
        StringBuilder output = new StringBuilder();

        for (String s : messages) {
            output.append(s + " ");
        }

        // parameters are inverted !
        JVMLogger.log(JVMLogger.TAG_PRINT, output.toString());
    }
    
    // parseInt native function
    private void jvm_parseInt(JVMStack stack, int numParams) throws LoadingException {
        SObjectRef struct = stack.getCurrentFrame().operandStack.pop();
        String str = readStringFromHeap((SObjectRef) struct);
        int parsedValue = Integer.parseInt(str);
        SInt intVal = new SInt(parsedValue);
        stack.getCurrentFrame().operandStack.push(intVal);
    }
}

package compactjvm.classfile;

import compactjvm.definitions.MethodAccessFlag;
import java.util.ArrayList;
import java.util.List;

/**
 * Definition of a method, usable mainly during the first call of the method,
 * when we need to load all these values from CP and process them in the instruction
 *
 * @author Adam Vesecky
 */
public final class MethodDefinition {
    private String methodClass;
    private String methodName;
    private String methodDescriptor;
    private List<String> methodParams;
    private String returnType;
    private int accessFlags;
    private MethodExcTableItem[] exceptionTable;
    
    public MethodDefinition(String methodClass, String methodName, String methodDescriptor, int accessFlags) {
        this.methodClass = methodClass;
        this.methodName = methodName;
        this.methodDescriptor = methodDescriptor;
        methodParams = new ArrayList<>();
        this.accessFlags = accessFlags;
        parseMethodDescriptor();
    }
    
    public String getMethodClass() {
        return methodClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<String> getMethodParams() {
        return methodParams;
    }

    public String getReturnType() {
        return returnType;
    }
    
    public void setExceptionTable(MethodExcTableItem[] excTable){
        this.exceptionTable = excTable;
    }
    
    public MethodExcTableItem[] getExceptionTable(){
        return exceptionTable;
    }
    
    public boolean isNativeMethod(){
        return (this.accessFlags & MethodAccessFlag.ACC_NATIVE) == MethodAccessFlag.ACC_NATIVE;
    }
    
    /**
     * Calculates number of words needed for allocation of parameters in an local variable array
     */
    public int getMethodParamsWordsCount() {
        int wordsCount = 0;
        for(String param : methodParams) {
            wordsCount += ("L".equals(param) || "D".equals(param)) ? 2 : 1;
        }
        return wordsCount;
    }

    public String getMethodDescriptor() {
        return methodDescriptor;
    }
    
    private void parseMethodDescriptor() {
        int paramsStart = methodDescriptor.indexOf("(") + 1;
        int paramsEnd = methodDescriptor.indexOf(")");
        String _params = methodDescriptor.substring(paramsStart, paramsEnd);
        String _returnType = methodDescriptor.substring(paramsEnd + 1);
        parseMethodParams(_params);
        parseReturnType(_returnType);
    }
    
    private void parseReturnType(String _returnType) {
        // may begin with [ as an array, followed by one letter or L
        returnType = _returnType.replaceAll("[L;]", ""); 
    }
    
    /**
     * Parses a string with parameters and creates a list, containing either
     * primitive field definition or class names
     * If the value begins with [, it represents an array of given type
     * Generics are not supported, because they suck
     */
    private void parseMethodParams(String _params) {
        boolean isArrayParam = false;
        boolean classNameReading = false;
        StringBuilder buff = new StringBuilder();
        char[] _paramsChars = _params.toCharArray();
        for(int i = 0; i < _paramsChars.length; ++i) {
            if(_paramsChars[i] == '[') {
                isArrayParam = true;
            } else if(_paramsChars[i] == 'L') {
                // begining to load name of the class
                classNameReading = true;
            } else if(_paramsChars[i] == ';') {
                // parsed a complete name of the class
                String className = buff.toString();
                methodParams.add((isArrayParam ? "[" : "") + className);
                buff.delete(0, buff.length());
                classNameReading = false;
                isArrayParam = false;
            } else if(classNameReading) {
                // loading name of the class -> between L and ;
                buff.append(_paramsChars[i]);
            } else {
                // loading letters identifying primitive types
                methodParams.add((isArrayParam ? "[" : "") + _paramsChars[i]);
                isArrayParam = false;
            }
        }
    }
}

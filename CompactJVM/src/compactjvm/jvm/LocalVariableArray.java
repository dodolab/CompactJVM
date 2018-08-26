
package compactjvm.jvm;

import compactjvm.exceptions.LoadingException;
import compactjvm.structures.*;


/**
 * 
 * Size of a word is represented by an integer
 * int, short, byte and float have size of 1 word, double and long have size of 2 words
 * 
 * If we invoke a method of an instance, we need to put a reference to "THIS" at the first
 * index of localVariables 
 * 
 * @author Adam Vesecky
 */
public class LocalVariableArray {
    
    /**
     * Array of words for all local variables
     */
    SStruct[] localVariables;
    
    /**
     * Size of an array of local variables, taken from ClassFile
     */
    public LocalVariableArray(int size) {
        localVariables = new SStruct[size];
    }
    
    public <T extends SStruct> T getVar(int index) throws LoadingException{
        SStruct ent = localVariables[index];
        T entity = (T)ent;
        
        if(entity == null) throw new LoadingException("Wrong type! Found: "+ent.toString());
        return entity;
    }
    
    public <T extends SStruct> void setVar(int index, T value) throws LoadingException {
        if(value == null) throw new LoadingException("Variable can't be null!");
        
        localVariables[index] = value;
    }
}

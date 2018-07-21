package cz.cvut.fit.compactjvm.attributes;

/**
 * Exception table item
 * 
 * @author Adam Vesecky
 */
public class AttrExcTableItem {
    // indicate the index into the code array at which the code for a new line in the original source file begins
    public int startPc;
    // valid index into the code array of the opcode of an instruction 
    public int endPc;
    // indicates the start of the exception handler
    public int handlerPc;
    // if non-zero, it is a valid index into constant pool; describes classinfo for catch exception type
    public int catchType;
}

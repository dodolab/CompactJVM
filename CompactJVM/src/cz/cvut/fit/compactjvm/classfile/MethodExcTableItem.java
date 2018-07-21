
package cz.cvut.fit.compactjvm.classfile;

import cz.cvut.fit.compactjvm.classfile.ClassFile;

/**
 * The same entity as AttrExcTableItem but it is used in MethodDefinition entity
 * 
 * @author Adam Vesecky
 */
public class MethodExcTableItem {
    // indicate the index into the code array at which the code for a new line in the original source file begins
    public int startPc;
    // valid index into the code array of the opcode of an instruction 
    public int endPc;
    // indicates the start of the exception handler
    public int handlerPc;
    // the class that is specified in catch block (e.g. catch(Exception e) )
    public ClassFile catchClass;
}

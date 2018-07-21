package cz.cvut.fit.compactjvm.attributes;

/**
 * Attribute code
 * @author Adam Vesecky
 */
public class AttrCode extends Attribute{
  // maximum depth of the operand stack of this method at any point during execution of the method. 
   public int maxStack;
   // the number of local variables in the local variable array allocated upon invocation of this method
   public int maxLocals;
   // the number of bytes in the code array for this method
   public int codeLength;
   // actual bytes of Java virtual machine code that implement the method
   public byte[] code;
   // number of entries in the exception_table table
   public int exceptionTableLength;
   // Each entry describes one exception handler in the code array. 
   public AttrExcTableItem[] exceptionTable;
   // indicates the number of attributes of the Code attribute. 
   public int attributesCount;
   public Attribute[] attrs;
}

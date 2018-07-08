package cz.cvut.fit.compactjvm.entities;

/**
 *
 * @author Adam Vesecky
 */
public class AttrCode extends Attribute{
   public int maxStack;
   public int maxLocals;
   public int codeLength;
   public byte[] code;
   public int exceptionTableLength;
   public AttrExcTableItem[] exceptionTable;
   
   public int attributesCount;
   public Attribute[] attrs;
}

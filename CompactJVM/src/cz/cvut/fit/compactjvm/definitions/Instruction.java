package cz.cvut.fit.compactjvm.definitions;

/**
 *
 * @author Adam Vesecky
 */
public class Instruction {
    //load onto the stack a reference from an array
    public static final int IN_AALOAD = 0x32;
    //store into a reference in an array
    public static final int IN_AASTORE = 0x53;
    //push a null reference onto the stack
    public static final int IN_ACONST_NULL = 0x1;
    // load a reference onto the stack from a local variable #index
    public static final int IN_ALOAD = 0x19;
    // load a reference onto the stack from local variable 0
    public static final int IN_ALOAD0 = 0x2a;
    // load a reference onto the stack from local variable 1
    public static final int IN_ALOAD1 = 0x2b;
    // load a reference onto the stack from local variable 2
    public static final int IN_ALOAD2 = 0x2c;
    // load a reference onto the stack from local variable 3
    public static final int IN_ALOAD3 = 0x2d;
    // load an int from an array
    public static final int IN_IALOAD = 0x2e;
    // create a new array of references of length count and component type identified by the class reference index (indexbyte1 << 8 + indexbyte2) in the constant pool
    public static final int IN_ANEWARRAY = 0xbd;
    // create new array with count elements of primitive type identified by atype
    public static final int IN_NEWARRAY = 0xbc; // 188
    // return a reference from a method
    public static final int IN_ARETURN = 0xb0;
    // get the length of an array
    public static final int IN_ARRAYLENGTH = 0xbe;
    // store a reference into a local variable #index
    public static final int IN_ASTORE = 0x3a;
    // store a reference into a local variable 0
    public static final int IN_ASTORE0 = 0x4b;
    // store a reference into a local variable 1
    public static final int IN_ASTORE1 = 0x4c;
    // store a reference into a local variable 2
    public static final int IN_ASTORE2 = 0x4d;
    // store a reference into local variable 3
    public static final int IN_ASTORE3 = 0x4e; // 78
    // get a static field value of a class, where the field is identified by field reference in the constant pool index (index1 << 8 + index2)
    public static final int IN_GETSTATIC = 0xb2;
    // set static field to value in a class, where the field is identified by a field reference index in constant pool (indexbyte1 << 8 + indexbyte2)
    public static final int IN_PUTSTATIC = 0xb3;
    // store int value into variable #index
    public static final int IN_ISTORE = 0x36;
    // store int value into variable 0
    public static final int IN_ISTORE0 = 0x3b; //59
    // store int value into variable 1
    public static final int IN_ISTORE1 = 0x3c; //60
    // store int value into variable 2
    public static final int IN_ISTORE2 = 0x3d; //61
    // store int value into variable 3
    public static final int IN_ISTORE3 = 0x3e; //62
    // store an int into an array
    public static final int IN_IASTORE = 0x4f;
    // load an int value from a local variable #index
    public static final int IN_ILOAD = 0x15; //21
    // load an int value from local variable 0
    public static final int IN_ILOAD0 = 0x1a; //26
    // load an int value from local variable 1
    public static final int IN_ILOAD1 = 0x1b; //27
    // load an int value from local variable 2
    public static final int IN_ILOAD2 = 0x1c; //28
    // load an int value from local variable 3
    public static final int IN_ILOAD3 = 0x1d; //29
    // add two ints
    public static final int IN_IADD = 0x60; //96
    // multiply two integers
    public static final int IN_IMUL = 0x68; //104
    // push a byte onto the stack as an integer value
    public static final int IN_BIPUSH = 0x10; //16
    // load the int value −1 onto the stack
    public static final int IN_ICONSTM1 = 0x2; //2
    // load the int value 0 onto the stack
    public static final int IN_ICONST0 = 0x3; //3
    // load the int value 1 onto the stack
    public static final int IN_ICONST1 = 0x4; //4
    // load the int value 2 onto the stack
    public static final int IN_ICONST2 = 0x5; //5
    // load the int value 3 onto the stack
    public static final int IN_ICONST3 = 0x6; //6
    // load the int value 4 onto the stack
    public static final int IN_ICONST4 = 0x7; //7
    // load the int value 5 onto the stack
    public static final int IN_ICONST5 = 0x8; //8
    
    // push the long 0 onto the stack
    public static final int IN_LCONST0 = 0x9;
    // push the long 1 onto the stack
    public static final int IN_LCONST1 = 0xa; // 10
    // invoke a static method and puts the result on the stack (might be void); the method is identified by method reference index in constant pool (indexbyte1 << 8 + indexbyte2)
    public static final int IN_INVOKESTATIC = 0xb8; //184
    // return void from method
    public static final int IN_RETURN = 0xb1; //177
    // return an integer from a method
    public static final int IN_IRETURN = 0xac; //172
   
    // if value is less than or equal to 0, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)
    public static final int IN_IFLE = 0x9e;
    // perform no operation
    public static final int IN_NOP = 0x0;
    // increment local variable #index by signed byte const
    public static final int IN_IINC = 0x84;
    // reserved for implementation-dependent operations within debuggers; should not appear in any class file
    public static final int IN_IMPDEP2 = 0xff;
    // goes to another instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)
    public static final int IN_GOTO = 0xa7;
    // push 0.0f on the stack
    public static final int IN_FCONST0 = 0x0b;
    // push 1.0f on the stack
    public static final int IN_FCONST1 = 0x0c;
    // push 2.0f on the stack
    public static final int IN_FCONST2 = 0x0d;
    // if value1 is greater than or equal to value2, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)
    public static final int IN_IF_ICMPGE = 0xa2;
}

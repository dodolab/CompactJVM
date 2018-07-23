package cz.cvut.fit.compactjvm.definitions;

/**
 * Codes of implemented instructions
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
    public static final int IN_ALOAD0 = 0x2a; //42
    // load a reference onto the stack from local variable 1
    public static final int IN_ALOAD1 = 0x2b;
    // load a reference onto the stack from local variable 2
    public static final int IN_ALOAD2 = 0x2c;
    // load a reference onto the stack from local variable 3
    public static final int IN_ALOAD3 = 0x2d;
    // load an byte from an array
    public static final int IN_BALOAD = 0x33;    
     // load an int from an array
    public static final int IN_IALOAD = 0x2e;
    // create a new array of references of length count and component type identified by the class reference index (indexbyte1 << 8 + indexbyte2) in the constant pool
    public static final int IN_ANEWARRAY = 0xbd;
    // create new array with count elements of primitive type identified by atype
    public static final int IN_NEWARRAY = 0xbc; // 188
    // return a reference from a method
    public static final int IN_ARETURN = 0xb0;
    // get the length of an array
    public static final int IN_ARRAYLENGTH = 0xbe; //190
    // store a reference into a local variable #index
    public static final int IN_ASTORE = 0x3a;
    // store a reference into a local variable 0
    public static final int IN_ASTORE0 = 0x4b; //75
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
    
     // load an long value from a local variable #index
    public static final int IN_LLOAD = 0x16; 
    // load an long value from local variable 0
    public static final int IN_LLOAD0 = 0x1e;
    // load an long value from local variable 1
    public static final int IN_LLOAD1 = 0x1f;
    // load an long value from local variable 2
    public static final int IN_LLOAD2 = 0x20;
    // load an long value from local variable 3
    public static final int IN_LLOAD3 = 0x21;
    
    
    // add two ints
    public static final int IN_IADD = 0x60; //96
    // substract two ints
    public static final int IN_ISUB = 0x64; //100
    // multiply two integers
    public static final int IN_IMUL = 0x68; //104
    // divide two integers
    public static final int IN_IDIV = 0x6c;
    
    // push a byte onto the stack as an integer value
    public static final int IN_BIPUSH = 0x10; //16
    // push a short onto the stack as an integer value
    public static final int IN_SIPUSH = 0x11; // 17
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
    // load the int value 5 onto the stack
    public static final int IN_DCONST0 = 0xe; //14
    // load the int value 5 onto the stack
    public static final int IN_DCONST1 = 0xf; //15
    
    // load an int from an array
    public static final int IN_CALOAD = 0x34; //52
    // load an int from an array
    public static final int IN_CASTORE = 0x55; //85
    
    // push the long 0 onto the stack
    public static final int IN_LCONST0 = 0x9;
    // push the long 1 onto the stack
    public static final int IN_LCONST1 = 0xa; // 10
    // invoke a static method and puts the result on the stack (might be void); the method is identified by method reference index in constant pool (indexbyte1 << 8 + indexbyte2)
    public static final int IN_INVOKESTATIC = 0xb8; //184
    // throw exception or error
    public static final int IN_ATHROW = 0xbf;
    // push item from run-time constant pool
    public static final int IN_LDC = 0x12;
    
    // return void from method
    public static final int IN_RETURN = 0xb1; //177
    // return an integer from a method
    public static final int IN_IRETURN = 0xac; //172
   
    // negates integer value
    public static final int IN_INEG = 0x74;
    // if value is less than or equal to 0, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)
    public static final int IN_IFLE = 0x9e;
    // if value is less than 0, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)
    public static final int IN_IFLT = 0x9b;
    // if value is greater than or equal to 0, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)
    public static final int IN_IFGE = 0x9c;
    // if value is greater than 0, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)
    public static final int IN_IFGT = 0x9d;
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
    // if value1 is greater than to value2, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)
    public static final int IN_IF_ICMPGT = 0xa3;
    // if value is null, branch to instruction at branchoffset
    public static final int IN_IF_NULL = 0xc6;
    // if value is not null, branch to instruction at branchoffset
    public static final int IN_IF_NON_NULL = 0xc7;
    // if value is equal to zero, branch to instruction at branchoffset
    public static final int IN_IF_EQ = 0x99;
    // if value is not equal to zero, branch to instruction at branchoffset
    public static final int IN_IF_NE = 0x9a;
    // if value1 == value2
    public static final int IN_IF_ICMPEQ = 0x9f;
    // if value1 != value2
    public static final int IN_IF_ICMPNE = 0xa0;
    
    // creates new instance of class defined by two next bytes
    public static final int IN_NEW = 0xbb; //187 (-69)
    //Duplicate the top operand stack value
    public static final int IN_DUP = 0x59; //89
    // Invoke instance method; special handling for superclass, private, and instance initialization method invocations
    public static final int IN_INVOKESPECIAL = 0xb7; //183
    // Invoke instance method; dispatch based on class
    public static final int IN_INVOKEVIRTUAL = 0xb6; //182
    // Pop the top operand stack value
    public static final int IN_POP = 0x57; //87
    // Set field in object
    public static final int IN_PUTFIELD = 0xb5; //181
    // Get field from object
    public static final int IN_GETFIELD = 0xb4; //180
}

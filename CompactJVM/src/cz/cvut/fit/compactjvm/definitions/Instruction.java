package cz.cvut.fit.compactjvm.definitions;

/**
 *
 * @author Adam Vesecky
 */
public class Instruction {
    public static final int IN_AALOAD = 0x32;
    public static final int IN_AASTORE = 0x53;
    public static final int IN_ACONST_NULL = 0x1;
    public static final int IN_ALOAD = 0x19;
    public static final int IN_ALOAD0 = 0x2a;
    public static final int IN_ALOAD1 = 0x2b;
    public static final int IN_ALOAD2 = 0x2c;
    public static final int IN_ALOAD3 = 0x2d;
    public static final int IN_ANEWARRAY = 0xbd;
    public static final int IN_ARETURN = 0xb0;
    public static final int IN_ARRAYLENGTH = 0xbe;
    public static final int IN_ASTORE = 0x3a;
    public static final int IN_GETSTATIC = 0xb2;
    public static final int IN_PUTSTATIC = 0xb3;
    public static final int IN_ISTORE = 0x36;
    
    //Integer operace - nacitani, nahravani do stacku, lokalnich pomennych,...
    public static final int IN_ISTORE0 = 0x3b; //59
    public static final int IN_ISTORE1 = 0x3c; //60
    public static final int IN_ISTORE2 = 0x3d; //61
    public static final int IN_ISTORE3 = 0x3e; //62
    public static final int IN_ILOAD = 0x15; //21
    public static final int IN_ILOAD0 = 0x1a; //26
    public static final int IN_ILOAD1 = 0x1b; //27
    public static final int IN_ILOAD2 = 0x1c; //28
    public static final int IN_ILOAD3 = 0x1d; //29
    public static final int IN_IADD = 0x60; //96
    public static final int IN_IMUL = 0x68; //104
    public static final int IN_BIPUSH = 0x10; //16
    public static final int IN_ICONSTM1 = 0x2; //2
    public static final int IN_ICONST0 = 0x3; //3
    public static final int IN_ICONST1 = 0x4; //4
    public static final int IN_ICONST2 = 0x5; //5
    public static final int IN_ICONST3 = 0x6; //6
    public static final int IN_ICONST4 = 0x7; //7
    public static final int IN_ICONST5 = 0x8; //8
    
    //Invoke methods and return values
    public static final int IN_INVOKESTATIC = 0xb8; //184
    public static final int IN_RETURN = 0xb1; //177
    public static final int IN_IRETURN = 0xac; //172
    // todo...
}

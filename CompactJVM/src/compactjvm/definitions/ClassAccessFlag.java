package compactjvm.definitions;

/**
 *
 * @author Adam Vesecky
 * Kapitola 4.1. https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html
 */
public class ClassAccessFlag {
    public static final int ACC_PUBLIC      = 0x0001;	//Declared public; may be accessed from outside its package.
    public static final int ACC_FINAL       = 0x0010;	//Declared final; no subclasses allowed.
    public static final int ACC_SUPER       = 0x0020;	//Treat superclass methods specially when invoked by the invokespecial instruction.
    public static final int ACC_INTERFACE   = 0x0200;	//Is an interface, not a class.
    public static final int ACC_ABSTRACT    = 0x0400;	//Declared abstract; must not be instantiated.
    public static final int ACC_SYNTHETIC   = 0x1000;	//Declared synthetic; not present in the source code.
    public static final int ACC_ANNOTATION  = 0x2000;	//Declared as an annotation type.
    public static final int ACC_ENUM        = 0x4000;	//Declared as an enum type.
}

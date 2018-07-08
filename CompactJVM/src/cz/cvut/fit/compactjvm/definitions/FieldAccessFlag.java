/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.definitions;

/**
 *
 * @author Nick Nemame
 */
public class FieldAccessFlag {
    public static final int ACC_PUBLIC      = 0x0001;	//Declared public; may be accessed from outside its package.
    public static final int ACC_PRIVATE     = 0x0002;	//Declared private; usable only within the defining class.
    public static final int ACC_PROTECTED   = 0x0004;	//Declared protected; may be accessed within subclasses.
    public static final int ACC_STATIC      = 0x0008;	//Declared static.
    public static final int ACC_FINAL       = 0x0010;	//Declared final; never directly assigned to after object construction (JLS §17.5).
    public static final int ACC_VOLATILE    = 0x0040;	//Declared volatile; cannot be cached.
    public static final int ACC_TRANSIENT   = 0x0080;	//Declared transient; not written or read by a persistent object manager.
    public static final int ACC_SYNTHETIC   = 0x1000;	//Declared synthetic; not present in the source code.
    public static final int ACC_ENUM        = 0x4000;	//Declared as an element of an enum.
}

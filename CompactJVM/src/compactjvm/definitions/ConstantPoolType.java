package compactjvm.definitions;

/**
 * Constant pool types
 * 
 * @author Adam Vesecky
 */
public class ConstantPoolType {
   public static final int CPT_Class = 7; 
   public static final int CPT_Fieldref = 9;
   public static final int CPT_Methodref = 10;
   public static final int CPT_InterfaceMethodref = 11;
   public static final int CPT_String = 8;
   public static final int CPT_Integer = 3;
   public static final int CPT_Float = 4;
   public static final int CPT_Long = 5;
   public static final int CPT_Double = 6;
   public static final int CPT_NameAndType = 12;
   public static final int CPT_Utf8 = 1;
   public static final int CPT_MethodHandle = 15;
   public static final int CPT_MethodType = 16;
   public static final int CPT_InvokeDynamic = 18;
}

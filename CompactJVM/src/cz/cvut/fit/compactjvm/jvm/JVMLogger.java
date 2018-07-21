package cz.cvut.fit.compactjvm.jvm;

import java.util.ArrayList;

/**
 * Very simple logger that uses tags that can be either enabled or disabled
 * @author Adam Vesecky
 */
public class JVMLogger {
    
    // parsing logger
    public static final String TAG_PARSING = "PARSING";
    // instruction executing logger
    public static final String TAG_INSTR = "INSTR";
    
    // loading instruction (that loads anything from heap, constant pool etc.)
    public static final String TAG_INSTR_LOAD = "INSTR_LOAD";
    // storing instruction
    public static final String TAG_INSTR_STORE = "INSTR_STORE";
    // jumps (goto, throw, if)
    public static final String TAG_INSTR_JUMP = "INSTR_JUMP";
    // invoking instructions
    public static final String TAG_INSTR_INVOKE = "INSTR_INVOKE";
    // push instructions
    public static final String TAG_INSTR_PUSH = "INSTR_PUSH";
    
    // other
    public static final String TAG_OTHER = "OTHER";
    // garbage collector
    public static final String TAG_GC = "GC";
    // heap
    public static final String TAG_HEAP = "HEAP";
    // console output from running program
    public static final String TAG_PRINT = "PRINT";
    
    private static final ArrayList<String> tags = new ArrayList<String>();
    private static int globalPadding = 0;
    
    /**
     *  increases padding so that next logs will be padded
     */
    public static void increaseGlobalPadding(int padding){
        if(globalPadding + padding > 0) globalPadding += padding;
    }
    
    /**
     *  decreases padding
     */
    public static void decreaseGlobalPadding(int padding){
        if(globalPadding - padding > 0) globalPadding-=padding;
    }
    
    /**
     * enables logging
     * @param tag 
     */
    public static void enableLogging(String tag){
        if(!tags.contains(tag)) tags.add(tag);
    }

    /**
     * disables logging
     * @param tag 
     */
    public static void disableLogging(String tag){
        if(tags.contains(tag)) tags.remove(tag);
    }
    
    public static boolean loggingEnabled(String tag){
        return tags.contains(tag);
    }
    
    public static void log(String tag, String msg){
        log(tag,msg,0);
    }
    
    public static void log(String tag, String msg, int padding){
        if(tags.contains(tag) || (tag.startsWith(TAG_INSTR) && tags.contains(TAG_INSTR))){
            System.out.print("["+tag+"]:: ");
            
            if(tag.startsWith(TAG_INSTR)){
                // toto je naprosta prasarna, ale nechtelo se mi lezt do kazde instrukce a menit log
                int instruct = CompactJVM.getInstance().getThreads().get(0).getStack().getCurrentInstructionIndex();
                System.out.print("["+instruct+"]");
            }
            
            for(int i=0; i<padding+globalPadding; i++) System.out.print(" ");
            System.out.println(msg);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Very simple logger
 * @author Adam Vesecky
 */
public class JVMLogger {
    
    // parsing logger
    public static final String TAG_PARSING = "PARSING";
    // instruction executing logger
    public static final String TAG_INSTR = "INSTR";
    // other - will be replaced continuously
    public static final String TAG_OTHER = "OTHER";
    // garbage collector
    public static final String TAG_GC = "GC";
    
    private static final ArrayList<String> tags = new ArrayList<String>();
    private static int globalPadding = 0;
    
    public static void increaseGlobalPadding(int padding){
        if(globalPadding + padding > 0) globalPadding += padding;
    }
    
    public static void enableLogging(String tag){
        if(!tags.contains(tag)) tags.add(tag);
    }
    
    public static void disableLogging(String tag){
        if(tags.contains(tag)) tags.remove(tag);
    }
    
    public static void log(String tag, String msg){
        log(tag,msg,0);
    }
    
    public static void log(String tag, String msg, int padding){
        if(tags.contains(tag)){
            System.out.print("["+tag+"]:: ");
            
            for(int i=0; i<padding+globalPadding; i++) System.out.print(" ");
            System.out.println(msg);
        }
    }
}

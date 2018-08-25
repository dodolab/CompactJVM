package compactjvm.natives;

import compactjvm.jvm.JVMStack;
import compactjvm.jvm.ObjectHeap;

/**
 * Common class for all native objects
 * @author Adam Vesecky
 */
public abstract class NativeObject {
    
    /**
     * Supplies the object constructor
     * @param stack 
     */
    public abstract void construct(JVMStack stack, ObjectHeap heap) throws Exception;
}

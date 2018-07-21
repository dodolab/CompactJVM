package cz.cvut.fit.compactjvm.natives;

import cz.cvut.fit.compactjvm.jvm.JVMStack;
import cz.cvut.fit.compactjvm.jvm.ObjectHeap;

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

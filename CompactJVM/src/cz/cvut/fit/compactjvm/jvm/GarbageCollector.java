package cz.cvut.fit.compactjvm.jvm;

import cz.cvut.fit.compactjvm.core.ClassFile;

/**
 * Tato trida implementuje kopirujici garbage collector.
 * Nejprve projde root, tzn. projde tridni promenne v method area, dale
 * projde vsechny thready JVM a pro kazdy thread projde zasobnik ramcu a v nem
 * pole lokalnich promennych a operand stack (ten potrebuji, protoze kdyz
 * funkce vrati referenci do haldy, tak je ulozena pouze v operand stacku)
 * @author Nick Nemame
 */
public class GarbageCollector {
    
    private final CompactJVM jvm;
    
    private final ObjectHeap heap;
    
    public GarbageCollector(CompactJVM jvm) {
        this.jvm = jvm;
        heap = jvm.getObjectHeap();
    }
    
    public void collect() {
        for(ClassFile classFile : jvm.getMethodArea().getLoadedClassFiles()) {
            collectMethodAreaData(classFile);
        }
        
        for(JVMThread thread : jvm.getThreads()) {
            for(StackFrame frame : thread.getStack().getAllFrames()) {
                collectStackFrameData(frame);
            }
        }
    }
    
    private void collectMethodAreaData(ClassFile classFile) {
        
    }
    
    private void collectStackFrameData(StackFrame frame) {
        //for(int reference : frame.references.getReferences()) {
            
        //}
    }
    
    public void initializeDataHeader() {
        //prazdne, nemam zadna hlavickova data
    }
    
    public int getRequiredHeaderBytes() {
        return 1; //GC si nepotrebuje nic pamatovat, potreboval by maximalne napr. TTL pri generativnim GC
    }
    
}

package cz.cvut.fit.compactjvm.jvm;

/**
 * Tato trida implementuje kopirujici garbage collector.
 * Nejprve projde root, tzn. projde tridni promenne v method area, dale
 * projde vsechny thready JVM a pro kazdy thread projde zasobnik ramcu a v nem
 * pole lokalnich promennych a operand stack (ten potrebuji, protoze kdyz
 * funkce vrati referenci do haldy, tak je ulozena pouze v operand stacku)
 * @author Nick Nemame
 */
public class GarbageCollector {
    
    private CompactJVM jvm;
    
    public GarbageCollector(CompactJVM jvm) {
        this.jvm = jvm;
    }
    
    public void collect() {
        
    }
    
    public void initializeDataHeader() {
        
    }
    
    public int getRequiredHeaderBytes() {
        return 0; //GC si nepotrebuje nic pamatovat, potreboval by maximalne napr. TTL pri generativnim GC
    }
    
}

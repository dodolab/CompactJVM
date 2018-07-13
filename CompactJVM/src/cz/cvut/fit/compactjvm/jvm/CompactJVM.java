package cz.cvut.fit.compactjvm.jvm;

import cz.cvut.fit.compactjvm.parsing.ClassFileLoader;
import cz.cvut.fit.compactjvm.classfile.ClassFile;
import cz.cvut.fit.compactjvm.exceptions.ParsingException;
import cz.cvut.fit.compactjvm.parsing.ClassFileParser;
import cz.cvut.fit.compactjvm.classfile.Word;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.exceptions.OutOfHeapMemException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Adam Vesecky
 */
public class CompactJVM {
    private static CompactJVM instance;
    private final MethodArea methodArea;
    private final ObjectHeap heap;
    
    private List<JVMThread> threads = new LinkedList<>();
    
    public static CompactJVM getInstance(){
        return instance;
    }
    
    public CompactJVM(){
        instance = this; // this is only for logging (logger sometimes needs to access the JVM)
        ClassFileLoader classLoader = new ClassFileLoader();
        methodArea = new MethodArea(classLoader);
        GarbageCollector garbageCollector = new GarbageCollector(this);
        heap = new ObjectHeap(methodArea, garbageCollector, 10000); //heap je pouze jedna pro jednu instanci JVM, tzn. thready ji sdili
    }
    
    public List<JVMThread> getThreads() {
        return threads;
    }
    
    public void loadApplication(String path) throws IOException, ParsingException{
        methodArea.initialLoad(path);
    }
    
    public void start() throws Exception{
        JVMThread thread = new JVMThread(methodArea, heap);
        threads.add(thread);
        thread.run("compactjvmlab/CompactJVMLab"); //@todo zatim nacitam fixne porad ten puvodni soubor
    }
    
    public MethodArea getMethodArea() {
        return methodArea;
    }
    
    public ObjectHeap getObjectHeap() {
        return heap;
    }
}

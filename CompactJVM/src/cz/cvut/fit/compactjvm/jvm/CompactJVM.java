package cz.cvut.fit.compactjvm.jvm;

import cz.cvut.fit.compactjvm.classloader.loading.ClassFileLoader;
import cz.cvut.fit.compactjvm.core.ClassFile;
import cz.cvut.fit.compactjvm.exceptions.ParsingException;
import cz.cvut.fit.compactjvm.classloader.parsing.ClassFileParser;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.exceptions.OutOfHeapMemException;
import java.io.IOException;

/**
 * 
 * @author Adam Vesecky
 */
public class CompactJVM {
    
    private final MethodArea methodArea;
    
    public CompactJVM(){
        ClassFileLoader classLoader = new ClassFileLoader();
        methodArea = new MethodArea(classLoader);
    }
    
    public void loadApplication(String path) throws IOException, ParsingException{
        methodArea.initialLoad(path);
    }
    
    public void start() throws LoadingException, ClassNotFoundException, OutOfHeapMemException{
        ObjectHeap heap = new ObjectHeap(methodArea, 10000);
        JVMThread thread = new JVMThread(methodArea, heap);
        thread.run("compactjvmlab/CompactJVMLab"); //@todo zatim nacitam fixne porad ten puvodni soubor
    }
}

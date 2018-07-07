package cz.cvut.fit.compactjvm.jvm;

import cz.cvut.fit.compactjvm.core.ClassFile;
import cz.cvut.fit.compactjvm.exceptions.ParsingException;
import cz.cvut.fit.compactjvm.parsing.ClassFileParser;
import java.io.IOException;

/**
 *
 * @author Adam Vesecky
 */
public class CompactJVM {
    
    private ClassFileParser classFileParser;
    
    public CompactJVM(){
        classFileParser = new ClassFileParser();
    }
    
    public void loadApplication(String path) throws IOException, ParsingException{
        ClassFile cls = classFileParser.parseClassFile(path);
    }
    
    public void start(){
        
    }
}

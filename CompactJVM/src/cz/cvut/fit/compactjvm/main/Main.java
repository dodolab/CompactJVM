package cz.cvut.fit.compactjvm.main;

import cz.cvut.fit.compactjvm.exceptions.ParsingException;
import cz.cvut.fit.compactjvm.jvm.CompactJVM;
import java.io.IOException;

/**
 *
 * @author Adam Vesecky
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParsingException {
        
        
        
        if (args.length < 1) {
            System.out.println("Missing path to class file!");
            return;
        }
        
        String classPath = args[0];
        CompactJVM jvm = new CompactJVM();
        jvm.loadApplication(classPath);
        jvm.start();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.classloader.loading;

import cz.cvut.fit.compactjvm.classloader.parsing.ClassFileParser;
import cz.cvut.fit.compactjvm.core.ClassFile;
import cz.cvut.fit.compactjvm.exceptions.ParsingException;
import cz.cvut.fit.compactjvm.jvm.CompactJVM;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nick Nemame
 */
public class ClassFileLoader {
    
    ClassFileParser parser;

    public ClassFileLoader() {
        this.parser = new ClassFileParser();
    }
    
    
    
    /**
     * Nacte class file a zparsuje jej. Zde bude implementovana metoda vyhledavani
     * souboru - bud striktne podle namespace nebo prohledavanim celeho CLASSPATH
     * @param className Fully quantified name
     * @return 
     */
    public ClassFile load(String className) {
        
        // load testing class file
        String classPath = "";
        if(className == "compactjvmlab/CompactJVMLab") {
            classPath = "../CompactJVMLab/build/classes/compactjvmlab/CompactJVMLab.class";
        } else {
            classPath = "../CompactJVMLab/build/classes/compactjvmlab/TestClass.class";
        }
        ClassFile classFile = null;
        try {
            classFile = parseFile(classPath);
        } catch (IOException | ParsingException ex) {
            Logger.getLogger(ClassFileLoader.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("exception");
        }
        return classFile;
    }
    
    public ClassFile parseFile(String path) throws IOException, ParsingException {
        ClassFile cls = parser.parseClassFile(path);
        return cls;
    }
    
}

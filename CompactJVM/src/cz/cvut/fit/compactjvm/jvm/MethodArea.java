/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm;

import cz.cvut.fit.compactjvm.core.ClassFile;
import cz.cvut.fit.compactjvm.classloader.loading.ClassFileLoader;
import cz.cvut.fit.compactjvm.exceptions.ParsingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tato trida uchovava parsovane definice trid.
 * Je spolecna pro vsechny instance JVM. Muze byt aplikovan garbage collector.
 * Pokud tato area neobsahuje pozadovanou tridu, pak vyuzije ClassFileLoaderu
 * k jejimu nacteni.
 * @todo Mel by byt thread-safe - If two threads are attempting to find a class named Lava, for example, and Lava has not yet been loaded, only one thread should be allowed to load it while the other one waits"
 * @todo Garbage collector - pokud je trida "unreferenced"
 * @todo Inside-Java-Virtual-Machine, str. 83, Method tables - organizace pro rychly pristup, tabulky instancnich metod
 * @author Nick Nemame
 */
public class MethodArea {
    
    private final ClassFileLoader classLoader;
    
    // simple class storage
    private final JVMClassStorage classStorage;

    public MethodArea(ClassFileLoader classLoader) {
        this.classLoader = classLoader;
        classStorage = new JVMClassStorage();
    }
    
    public JVMClassStorage getClassStorage(){
        return classStorage;
    }
    
    /**
     * Vrati ClassFile, pokud jej JVM jeste nema naparsovany, musi jej nejprve
     * nacist a pak naparsovat - momentalne takto lazy-load.
     * @param className
     * @return 
     */
    public ClassFile getClassFile(String className) {
        if(classStorage.containsClass(className)) {
            try {
                return classStorage.getClass(className);
            } catch (ClassNotFoundException ex) {
               // never thrown :-)
            }
        }
        ClassFile classFile = classLoader.load(className);
        classStorage.addClass(classFile);
        return classFile;
    }
    
    public void initialLoad(String classPath) {
        //return "";
    }

};

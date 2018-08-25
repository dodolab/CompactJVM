package compactjvm.jvm;

import compactjvm.classfile.ClassFile;
import compactjvm.parsing.ClassFileLoader;
import java.io.IOException;
import java.util.List;

/**
 * Tato trida uchovava parsovane definice trid.
 * Je spolecna pro vsechny instance JVM. Muze byt aplikovan garbage collector.
 * Pokud tato area neobsahuje pozadovanou tridu, pak vyuzije ClassFileLoaderu
 * k jejimu nacteni.
 * @todo Mel by byt thread-safe - If two threads are attempting to find a class named Lava, for example, and Lava has not yet been loaded, only one thread should be allowed to load it while the other one waits"
 * @todo Garbage collector - pokud je trida "unreferenced"
 * @todo Inside-Java-Virtual-Machine, str. 83, Method tables - organizace pro rychly pristup, tabulky instancnich metod
 * @author Adam Vesecky
 */
public class MethodArea {
    
    private final ClassFileLoader classLoader;
    private String mainClass;
    private String[] arguments;
    
    // simple class storage
    private final JVMClassStorage classStorage;
    
    
    public MethodArea(ClassFileLoader classLoader) {
        this.classLoader = classLoader;
        classStorage = new JVMClassStorage();
    }
    
    public JVMClassStorage getClassStorage(){
        return classStorage;
    }
    
    
    public void initialize(String classPath, String libraryPath, String mainClass, String[] arguments){
        classLoader.setClassPath(classPath);
        classLoader.setLibraryPath(libraryPath);
        this.mainClass = mainClass;
        this.arguments = arguments;
    }
    
    /**
     * Vrati ClassFile, pokud jej JVM jeste nema naparsovany, musi jej nejprve
     * nacist a pak naparsovat - momentalne takto lazy-load.
     * V pripade, ze nacitana Class dedi nejakou jinou tridu, je v zapeti rekurzivne
     * nactena i tato nadtrida. V ClassFile potom existuje vazba na nadtridu
     * a zaroven se z nadtridy prepocitaji indexy vlastnosti tridy v halde.
     * @param className
     * @return 
     */
    public ClassFile getClassFile(String className) throws IOException {
        if(classStorage.containsClass(className)) {
            try {
                return classStorage.getClass(className);
            } catch (ClassNotFoundException ex) {
               // never thrown :-)
            }
        }
      
        
        ClassFile classFile = classLoader.load(className);
        classStorage.addClass(classFile);
        
        //Nacitani podtrid a vytvoreni vazeb mezi ClassFile objekty rodicu a potomku
        ClassFile _classFile = classFile;
        boolean superClassAlreadyLoaded = false;
        while(_classFile.getSuperclassName() != null && !superClassAlreadyLoaded) {
            String superClassName = _classFile.getSuperclassName();
            if(!classStorage.containsClass(superClassName)) {
                ClassFile _superClassFile = classLoader.load(superClassName);
                classStorage.addClass(_superClassFile);
                _classFile.superClass = _superClassFile;
                _classFile = _superClassFile;
            } else {
                try {
                _classFile.superClass = classStorage.getClass(_classFile.getSuperclassName());
                } catch (ClassNotFoundException e) {
                    // trida by jiz mela byt nactena, nic by se nemelo stat
                }
                superClassAlreadyLoaded = true;
            }
        }
    
        recalculateFieldOffsets(classFile);
        
        return classFile;
    }
    
    public boolean isSuperClass(ClassFile parent, ClassFile child) throws IOException{
        if(parent.getClassName().equals(child.getClassName())) return true;
        else if(child.getSuperclassName() != null){
            ClassFile superChild = getClassFile(child.getClassName());
            if(superChild != null) return isSuperClass(parent,superChild);
            else return false;
        }
        return false;
    }
    
    
    public List<ClassFile> getLoadedClassFiles() {
        return classStorage.getClassFiles();
    }
    
    
    public String getMainClass(){
        return mainClass;
    }
    
    public String[] getArgs(){
        return arguments;
    }
    
    /**
     * Bez informace o predcich tridy nejsme schopni presne spocitat offset vlastnosti
     * v halde. Proto prepocitame
     */
    private void recalculateFieldOffsets(ClassFile classFile) throws IOException {
        if(!classFile.fieldOffsetsRecalculated) {
            if(classFile.getSuperclassName() != null) {
                ClassFile superClassFile = getClassFile(classFile.getSuperclassName());
                recalculateFieldOffsets(superClassFile);
                int superClassDataOffset = superClassFile.recursiveFieldCount;
                for(int i = 0; i < classFile.fieldInfos.length; ++i) {
                    classFile.fieldInfos[i].dataFieldOffset += superClassDataOffset;
                }
                classFile.recursiveFieldCount += superClassDataOffset;
            }
            classFile.fieldOffsetsRecalculated = true;
        }
    }
}
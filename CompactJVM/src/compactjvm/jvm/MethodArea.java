package compactjvm.jvm;

import compactjvm.classfile.ClassFile;
import compactjvm.parsing.ClassFileLoader;
import java.io.IOException;
import java.util.List;

/**
 * Parsed definitions of all classes, can be applied on Garbage collector
 * If we are looking for a class and we can't find it inside MethodArea,
 * we need to go into ClassFileLoader and load that particular class
 * 
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
     * Returns a classfile
     * If the classfile hasn't been parsed yet, it will be lazy-loaded
     * If the class is inheriting from another class, we need to load the 
     * parent class as well
     * @param className
     * @return 
     */
    public ClassFile getClassFile(String className) throws IOException {
        if(classStorage.containsClass(className)) {
            try {
                return classStorage.getClass(className);
            } catch (ClassNotFoundException ex) {
               // will never happen (LOL)
            }
        }
      
        ClassFile classFile = classLoader.load(className);
        classStorage.addClass(classFile);
        
        // loading parent classes and bindings 
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
                    // will never happen (LOL)
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
     * Field offsets need to be recalculated, because without information about 
     * parent classes we aren't able to calculate offsets precisely
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

package compactjvm.parsing;

import compactjvm.classfile.ClassFile;
import compactjvm.exceptions.ParsingException;
import java.io.IOException;
import compactjvm.jvm.JVMLogger;
import java.io.File;

/**
 * Class file loader
 * @author Adam Vesecky
 */
public class ClassFileLoader {

    private String classPath;
    private String libraryPath;
    private ClassFileParser parser;

    public ClassFileLoader() {
        this.parser = new ClassFileParser();
    }

    /**
     * Sets path to the class files of the running application
     * @param classPath 
     */
    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    /**
     * Sets path to the library class files (project CompactJVMLib)
     * @param libraryPath 
     */
    public void setLibraryPath(String libraryPath) {
        this.libraryPath = libraryPath;
    }

    /**
     * Gets path to the class files of the running application
     * @return 
     */
    public String getClassPath() {
        return classPath;
    }

    /**
     * Gets path to the library class files (project CompactJVMLib)
     * @return 
     */
    public String getLibraryPath() {
        return libraryPath;
    }

    /**
     * Loads class file and parses it into structures from compactjvm.classfile
     *
     */
    public ClassFile load(String className) throws IOException {

        // load testing class file
        String fullPath = classPath + className + ".class";

        // try program path and library path
        if (!new File(fullPath).exists()) {
            fullPath = libraryPath + className + ".class";
            if (!new File(fullPath).exists()) {
                throw new IOException("Cannot parse file " + fullPath + ". File may not exist.");
            }
        }

        ClassFile classFile = null;
        try {
            classFile = parseFile(fullPath);
        } catch (IOException | ParsingException ex) {
            JVMLogger.log(JVMLogger.TAG_OTHER, "Cannot parse file " + fullPath + ". File may not exist.");
        }
        return classFile;
    }

    public ClassFile parseFile(String path) throws IOException, ParsingException {
        ClassFile cls = parser.parseClassFile(path);
        return cls;
    }
}


package compactjvm.jvm;

import compactjvm.classfile.ClassFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple storage for loaded class files
 * @author Adam Vesecky
 */
public class JVMClassStorage {
    
    // map of class names and their indices (index is incremented with every  class file that has been parsed)
    private Map<String, Integer> classTable = new HashMap<String, Integer>();
    // list of class objects; index of the map below points to the specific class
    private ArrayList<ClassFile> classes = new ArrayList<ClassFile>();
    private int classCounter;
    
    
	public void addClass(ClassFile cls) {
		cls.index = classCounter;
		classTable.put(cls.getClassName(), classCounter);
		classes.add(cls);
		classCounter++;
	}

	public ClassFile getClass(String name) throws ClassNotFoundException {
		Integer address = classTable.get(name);
		if (address == null) {
			throw new ClassNotFoundException(name);
		}
		return classes.get(address);
	}

        public boolean containsClass(String name){
            return classTable.get(name) != null;
        }
        
	public ClassFile getClass(int address) {
		return classes.get(address);
	}
        
        public List<ClassFile> getClassFiles() {
            return classes;
        }
    
}

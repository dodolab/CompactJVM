package cz.cvut.fit.compactjvm.parsing;

import cz.cvut.fit.compactjvm.core.ClassFile;
import cz.cvut.fit.compactjvm.exceptions.ParsingException;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author Adam Vesecky
 */
public class ClassFileParser {
    
    public ClassFile parseClassFile(String path) throws IOException, ParsingException{
        File f = new File(path);
	
        if (!f.exists()) {
	  throw new IOException("File "+path+" doesn't exist!");		
        }
        
        FileInputStream fs = new FileInputStream(f);
	DataInputStream ds = new DataInputStream(fs);
        return parseClassFile(ds);
    }
    
    public ClassFile parseClassFile(DataInputStream str) throws IOException, ParsingException{
        ClassFile cls = new ClassFile();
        
        if(
                !checkClassFile(str) || 
                !parseVersion(str,cls)) 
            throw new ParsingException("Invalid class file");

        return cls;
    }
    
    
    	/**
	 * Checks whether first 4 bytes contain 0xCA, 0xFE, 0xBA, 0xBE
	 */
	private boolean checkClassFile(DataInputStream dis) throws IOException {
            int bt = dis.readInt();
            if(bt != 0xCAFEBABE){
                System.out.println("Error while reading class file -> it must begin with bytes 0xCAFEBABE");
                return false;
            }
            return true;
	}
        
        /**
	 * 5th and 6th bit: minor version
         * 7th and 8th bit: major version
	 */
	private boolean parseVersion(DataInputStream dis, ClassFile cls) throws IOException {
		short minor = dis.readShort();
                short major = dis.readShort();
                
                System.out.println("Minor version: "+minor);
                System.out.println("Major version: "+major);
                
                cls.majorVersion = major;
                cls.minorVersion = minor;
                
                // todo: some validation here

		return true;
	}
}

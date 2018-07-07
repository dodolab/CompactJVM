package cz.cvut.fit.compactjvm.parsing;

import cz.cvut.fit.compactjvm.core.ClassFile;
import cz.cvut.fit.compactjvm.definitions.ConstantPoolType;
import cz.cvut.fit.compactjvm.entities.CPEntity;
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

    public ClassFile parseClassFile(String path) throws IOException, ParsingException {
        File f = new File(path);

        if (!f.exists()) {
            throw new IOException("File " + path + " doesn't exist!");
        }

        FileInputStream fs = new FileInputStream(f);
        DataInputStream ds = new DataInputStream(fs);
        return parseClassFile(ds);
    }

    /**
     * Parses class file from input stream
     *
     * @param str stram to parse
     * @return parsed class file
     * @throws IOException
     * @throws ParsingException if parsing wasn't successfull (invalid class
     * file)
     */
    public ClassFile parseClassFile(DataInputStream str) throws IOException, ParsingException {

        // fill class file 
        ClassFile cls = new ClassFile();
        if (!fillClassFile(str, cls)) {
            throw new ParsingException("Invalid class file");
        }

        return cls;
    }

    private boolean fillClassFile(DataInputStream str, ClassFile cls) throws IOException {

        // parse everything, first fail stops the parsing
        if (!checkClassFile(str)) {
            return false;
        }
        if (!parseVersion(str, cls)) {
            return false;
        }
        if (!parseConstantPool(str, cls)) {
            return false;
        }
        if (!parseClassDeclaration(str, cls)) {
            return false;
        }
        if (!parseFields(str, cls)) {
            return false;
        }
        if (!parseMethods(str, cls)) {
            return false;
        }
        if (!parseAttributes(str, cls)) {
            return false;
        }

        return true;
    }


    /**
     * Checks whether first 4 bytes contain 0xCA, 0xFE, 0xBA, 0xBE
     */
    private boolean checkClassFile(DataInputStream dis) throws IOException {
        int bt = dis.readInt();
        if (bt != 0xCAFEBABE) {
            System.out.println("Error while reading class file -> it must begin with bytes 0xCAFEBABE");
            return false;
        }
        return true;
    }

    /**
     * 5th and 6th bit: minor version 7th and 8th bit: major version
     */
    private boolean parseVersion(DataInputStream dis, ClassFile cls) throws IOException {
        short minor = dis.readShort();
        short major = dis.readShort();

        System.out.println("Minor version: " + minor);
        System.out.println("Major version: " + major);

        cls.majorVersion = major;
        cls.minorVersion = minor;

        // todo: some validation here
        return true;
    }
    
    private boolean parseConstantPool(DataInputStream dis, ClassFile cls) throws IOException {
        int poolSize = dis.readUnsignedShort();
        ConstantPoolParser cpParser = new ConstantPoolParser();

        for (int i = 0; i < poolSize; i++) {
            CPEntity ent = cpParser.parseConstantPoolEntity(dis);
            // in case of error, entity will be null
            if(ent == null) return false;
            cls.cpEntities.add(ent);
        }
        return true;
    }

    private boolean parseClassDeclaration(DataInputStream dis, ClassFile cls) throws IOException {
        return true;
    }

    private boolean parseFields(DataInputStream dis, ClassFile cls) throws IOException {
        return true;
    }

    private boolean parseMethods(DataInputStream dis, ClassFile cls) throws IOException {
        return true;
    }

    private boolean parseAttributes(DataInputStream dis, ClassFile cls) throws IOException {
        return true;
    }
}

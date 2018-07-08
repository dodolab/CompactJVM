package cz.cvut.fit.compactjvm.classloader.parsing;

import cz.cvut.fit.compactjvm.core.ClassFile;
import cz.cvut.fit.compactjvm.entities.CPEntity;
import cz.cvut.fit.compactjvm.entities.Attribute;
import cz.cvut.fit.compactjvm.entities.FLEntity;
import cz.cvut.fit.compactjvm.entities.MTHEntity;
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

    private boolean fillClassFile(DataInputStream str, ClassFile cls) throws IOException, ParsingException {

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
        if (!parseAccessFlags(str, cls)) {
            return false;
        }
        if (!parseThis(str, cls)) {
            return false;
        }
        if (!parseSuper(str, cls)) {
            return false;
        }
        if (!parseInterfaces(str, cls)) {
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
        
        System.out.println("Classfile successfully loaded");

        return true;
    }

    /**
     * Checks whether first 4 bytes contain 0xCA, 0xFE, 0xBA, 0xBE
     */
    private boolean checkClassFile(DataInputStream dis) throws IOException {
        System.out.println("Checking class file");
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
        System.out.println("Parsing version");
        short minor = dis.readShort();
        short major = dis.readShort();

        System.out.println("Minor version: " + minor);
        System.out.println("Major version: " + major);

        cls.majorVersion = major;
        cls.minorVersion = minor;

        // todo: some validation here
        return true;
    }

    /**
     * 9th and 10th bit: cst pool size, then constant pool[cpsize-1]
     */
    private boolean parseConstantPool(DataInputStream dis, ClassFile cls) throws IOException {
        System.out.println("Parsing constant pool");
        int poolSize = dis.readUnsignedShort();
        System.out.println("Pool size: " + poolSize);

        // according to the specification, size is poolSize-1 :-)
        cls.cpEntities = new CPEntity[poolSize];

        ConstantPoolParser cpParser = new ConstantPoolParser();

        // first index is 1
        for (int i = 1; i < poolSize;) {
            CPEntity ent = cpParser.parseConstantPoolEntity(dis);
            // in case of error, entity will be null
            if (ent == null) {
                return false;
            }

            ent.byteIndex = i;
            cls.cpEntities[i] = ent;

            // increment by size of this entity
            i += ent.getSize();
        }
        return true;
    }

    /**
     *
     * 1st and 2nd bit after CP: access flags
     */
    private boolean parseAccessFlags(DataInputStream dis, ClassFile cls) throws IOException {
        System.out.println("Parsing access flags");
        short accessFlags = dis.readShort();
        cls.accessFlags = accessFlags;

        System.out.println("Access flags: " + Integer.toHexString(accessFlags));

        return true;
    }

    /**
     *
     * 3rd and 4th bit after CP: THIS class
     */
    private boolean parseThis(DataInputStream dis, ClassFile cls) throws IOException {
        System.out.println("Parsing this");

        short index = dis.readShort();
        cls.thisClassIndex = index;
        System.out.println("This class index: " + index);
        return true;
    }

    /**
     *
     * 5th and 6th bit after CP: SUPER class
     */
    private boolean parseSuper(DataInputStream dis, ClassFile cls) throws IOException {
        System.out.println("Parsing super");

        short index = dis.readShort();
        cls.superClassIndex = index;
        System.out.println("Super class index: " + index);
        return true;
    }

    /**
     *
     * 7th and 8th bit: interfaces_count, then interfaces[interfaces_count]
     */
    private boolean parseInterfaces(DataInputStream dis, ClassFile cls) throws IOException {
        System.out.println("Parsing interfaces");
        short intCount = dis.readShort();
        cls.interfaceCount = intCount;
        System.out.println("Interface count: " + intCount);

        cls.interfIndices = new int[cls.interfaceCount];

        // read interface indices
        for (int i = 0; i < intCount; i++) {
            int index = dis.readShort();
            cls.interfIndices[i] = index;
        }

        return true;
    }

    private boolean parseFields(DataInputStream dis, ClassFile cls) throws IOException, ParsingException {
        System.out.println("Parsing fields");
        short fldCount = dis.readShort();
        cls.fieldCount = fldCount;
        System.out.println("Field count: " + fldCount);

        FieldInfoParser fldParser = new FieldInfoParser();
        cls.fieldInfos = new FLEntity[fldCount];

        for (int i = 0; i < fldCount; i++) {
            FLEntity ent = fldParser.parseFieldEntity(cls, dis);
            // in case of error, entity will be null
            if (ent == null) {
                return false;
            }

            cls.fieldInfos[i] = ent;
        }

        return true;
    }

    private boolean parseMethods(DataInputStream dis, ClassFile cls) throws IOException, ParsingException {
        System.out.println("Parsing methods");
        short methodCnt = dis.readShort();
        cls.methodCount = methodCnt;
        System.out.println("Method count: " + methodCnt);

        MethodInfoParser mthParser = new MethodInfoParser();
        cls.methodInfos = new MTHEntity[methodCnt];

        for (int i = 0; i < methodCnt; i++) {
            MTHEntity ent = mthParser.parseMethodEntity(cls, dis);
            // in case of error, entity will be null
            if (ent == null) {
                return false;
            }

            cls.methodInfos[i] = ent;
        }

        return true;
    }

    private boolean parseAttributes(DataInputStream dis, ClassFile cls) throws IOException, ParsingException {
        System.out.println("Parsing attributes");
        short attrsCnt = dis.readShort();
        cls.attributeCount = attrsCnt;
        System.out.println("Attributes count: " + attrsCnt);

        if (cls.attributeCount != 0) {

            cls.attributeInfos = new Attribute[cls.attributeCount];
            
            AttributeParser parser = new AttributeParser();
            
            for (int i = 0; i < attrsCnt; i++) {
                Attribute attr = parser.parseAttributeEntity(cls,dis);
                cls.attributeInfos[i] = attr;
            }

        }
        return true;
    }
}

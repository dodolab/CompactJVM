package cz.cvut.fit.compactjvm.core;

import cz.cvut.fit.compactjvm.entities.CPEntity;
import java.util.ArrayList;

/**
 *
 * @author Adam Vesecky
 */
public class ClassFile {
    // todo: generate getters and setters later
    public short majorVersion;
    public short minorVersion;
    public short cpSize; // constant pool size
    
    public ArrayList<CPEntity> cpEntities = new ArrayList<CPEntity>();
}

package cz.cvut.fit.compactjvm.structures;

/**
 * Boolean structure
 * 
 * @author Adam Vesecky
 */
public class SBoolean extends SIntable {
    private final boolean value;


    public SBoolean(boolean value) {
	this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public SStruct makeCopy() {
	return new SBoolean(this.value);
     }

    @Override
    public String toString() {
        return "bool("+value +")";
    }

    @Override
    public SInt toInt() {
        return new SInt(value ? 1 : 0);
    }
    
}

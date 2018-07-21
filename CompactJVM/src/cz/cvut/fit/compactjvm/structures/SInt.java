package cz.cvut.fit.compactjvm.structures;

/**
 * Integer structure
 * 
 * @author Adam Vesecky
 */
public class SInt extends SIntable{
    private final int value;


    public SInt(int value) {
	this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public SInt makeCopy() {
	return new SInt(this.value);
     }

    @Override
    public String toString() {
        return "int("+value+")";
    }

    @Override
    public SInt toInt() {
        return this;
    }
}

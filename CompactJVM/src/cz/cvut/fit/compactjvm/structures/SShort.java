package cz.cvut.fit.compactjvm.structures;

/**
 * Short structure
 * 
 * @author Adam Vesecky
 */
public class SShort extends SIntable{
    private final short value;


    public SShort(short value) {
	this.value = value;
    }

    public short getValue() {
        return value;
    }

    @Override
    public SShort makeCopy() {
	return new SShort(this.value);
     }

    @Override
    public String toString() {
        return "short("+value+")";
    }
    
    @Override
    public SInt toInt() {
        return new SInt(value);
    }
}

package compactjvm.structures;

/**
 * Byte structure
 * 
 * @author Adam Vesecky
 */
public class SByte extends SIntable {
    private byte value;


    public SByte(byte value) {
	this.value = value;
    }

    public byte getValue() {
        return value;
    }
    
    @Override
    public SByte makeCopy() {
	return new SByte(this.value);
     }

    @Override
    public String toString() {
        return "byte("+value+")"+"<id:"+this.id+">";
    }

    @Override
    public SInt toInt() {
        return new SInt(value);
    }

    @Override
    public void negate() {
        value = (byte) -value;
    }
    
}

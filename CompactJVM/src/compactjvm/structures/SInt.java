package compactjvm.structures;

/**
 * Integer structure
 * 
 * @author Adam Vesecky
 */
public class SInt extends SIntable{
    private int value;


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
        return "int("+value+")"+"<id:"+this.id+">";
    }

    @Override
    public SInt toInt() {
        return this;
    }
    
    @Override
    public void negate() {
        value = -value;
    }
    
}

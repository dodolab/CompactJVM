package compactjvm.structures;

/**
 * Long structure
 * 
 * @author Adam Vesecky
 */
public class SLong extends SStruct {
    private final long value;


    public SLong(long value) {
	this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public SLong makeCopy() {
	return new SLong(this.value);
     }

    @Override
    public String toString() {
        return "long("+value+")"+"<id:"+this.id+">";
    }
}

package cz.cvut.fit.compactjvm.structures;

/**
 * Double structure
 * 
 * @author Adam Vesecky
 */
public class SDouble extends SStruct{
    private final double value;


    public SDouble(double value) {
	this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public SDouble makeCopy() {
	return new SDouble(this.value);
     }

    @Override
    public String toString() {
        return "double("+value+")"+"<id:"+this.id+">";
    }
}

package cz.cvut.fit.compactjvm.structures;

/**
 * Character structure
 * 
 * @author Adam Vesecky
 */
public class SChar extends SIntable{
    private final char value;


    public SChar(char value) {
	this.value = value;
    }

    public char getValue() {
        return value;
    }

    @Override
    public SChar makeCopy() {
	return new SChar(this.value);
     }

    @Override
    public String toString() {
        return "char("+value+"(";
    }
    
    @Override
    public SInt toInt() {
        return new SInt(value);
    }
    
    @Override
    public void negate() {
        //throw new Exception("Char cannot negate");
    }
    
}

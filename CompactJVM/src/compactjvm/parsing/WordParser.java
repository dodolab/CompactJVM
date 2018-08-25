package compactjvm.parsing;

import java.nio.ByteBuffer;

/**
 * Word parser
 * 
 * @author Adam Vesecky
 */
public class WordParser {
    
    public static final int SIZE = 4;
    
    public static final int BOOLEAN_WORDS = 1;
    public static final int INT_WORDS = 1;
    public static final int CHAR_WORDS = 1;
    public static final int BYTE_WORDS = 1;
    public static final int SHORT_WORDS = 1;
    public static final int REFERENCE_WORDS = 1;
    public static final int FLOAT_WORDS = 1;
    public static final int LONG_WORDS = 2;
    public static final int DOUBLE_WORDS = 2;
    
    /**
     * Parses integer from byte
     */
    public static int fromByte(byte b) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(SIZE);
        byteBuffer.put(3, b);

        return byteBuffer.getInt(0);
    }
    
    /**
     * Parses integer from byte array
     */
    public static int fromByteArray(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(SIZE);
        byteBuffer.put(3, bytes[1]);
        byteBuffer.put(2, bytes[0]);

        return byteBuffer.getInt(0);
    }
}

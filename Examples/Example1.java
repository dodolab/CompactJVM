

import cz.cvut.fit.compactjvm.proxies.*;
import java.io.IOException;

/**
 * Simple class that will be used for testing CompactJVM project
 * Just write anything, compile into class file and load it, using CompactJVM
 * @author Adam Vesecky
 */
public class Example1 {


    public static void main(String[] args) throws IOException {
		
		JVMFunctions.println("Test na konzoli");
		String pokus = "Test 123";

		/*TextReader reader = new TextReader("file.txt");
		String rd = reader.readLine();
		
		TextWriter writer = new TextWriter("text.txt");
		writer.appendLine("Hello world!");
		writer.close();
		*/
    }
}

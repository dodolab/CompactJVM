package cz.cvut.fit.compactjvm.jvm;

import cz.cvut.fit.compactjvm.jvm.CompactJVM;


/**
 *
 * @author Adam Vesecky
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length == 1 && args[0].equals("--help")) {
            System.out.println("Usage:\n"
                    + "parameter 1:\tPath to class root directory (including ending slash)\n"
                    + "parameter 2:\tNamespace of main class"
                    + "parameter 3:\tArguments (divided by spaces) of executing program"
                    + "\n"
                    + "Example: \n"
                    + "java -jar CompactJVM.jar ../../CompactJVMLab/build/classes/ compactjvmlab/CompactJVMLab '../cnf1.txt ../cnf1-result.txt'");
            return;
        }
        
        // load testing class file
        args = new String[]{"../CompactJVMLab/build/classes/compactjvmlab/CompactJVMLab.class"};
        
        try {

            if (args.length < 1) {
                System.out.println("Missing path to class file!");
                return;
            }

            JVMLogger.enableLogging(JVMLogger.TAG_INSTR);
            JVMLogger.enableLogging(JVMLogger.TAG_OTHER);
            //JVMLogger.enableLogging(JVMLogger.TAG_HEAP);
            JVMLogger.enableLogging(JVMLogger.TAG_GC);
            JVMLogger.enableLogging(JVMLogger.TAG_PRINT);
            
            String classPath = args[0];
            CompactJVM jvm = new CompactJVM();
            jvm.loadApplication(classPath);
            jvm.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

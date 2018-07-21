package cz.cvut.fit.compactjvm.jvm;


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
                    + "parameter 2:\tPath to libraries root directory (including ending slash)"
                    + "parameter 3:\tNamespace of main class"
                    + "parameter 4:\tArguments (divided by spaces) of executing program"
                    + "\n"
                    + "Example: \n"
                    + "java -jar CompactJVM.jar ../SatSolver/build/classes/ ../CompactJvmLab/build/classes compactjvmlab/CompactJVMLab '../cnf1.txt ../cnf1-result.txt'");
            return;
        }
        
        // load testing class file
        args = new String[]{
        "../SatSolver/build/classes/",
        "../CompactJvmLib/build/classes/",
        "compactjvmlab/CompactJVMLab", 
        //"../cnf1.txt" // argument
        "F:\\cnf.txt"
        };
        
        
        try {

            if (args.length < 1) {
                System.out.println("Missing path to class file!");
                return;
            }

            
            // here you can enable or disable logging
            
            // if TAG_INSTR is enabled, every instruction is logged !!
            //JVMLogger.enableLogging(JVMLogger.TAG_INSTR);
            JVMLogger.enableLogging(JVMLogger.TAG_INSTR_INVOKE);
            JVMLogger.enableLogging(JVMLogger.TAG_INSTR_JUMP);
            //JVMLogger.enableLogging(JVMLogger.TAG_INSTR_LOAD);
            //JVMLogger.enableLogging(JVMLogger.TAG_INSTR_PUSH);
            //JVMLogger.enableLogging(JVMLogger.TAG_INSTR_STORE);

            JVMLogger.enableLogging(JVMLogger.TAG_OTHER);
            //JVMLogger.enableLogging(JVMLogger.TAG_HEAP);
            JVMLogger.enableLogging(JVMLogger.TAG_GC);
            JVMLogger.enableLogging(JVMLogger.TAG_PRINT);
            
            String classPath = args[0];
            String libraryPath = args[1];
            String mainClass = args[2];
            String[] arguments = new String[args.length-3];
            
            for(int i=0; i<arguments.length; i++){
                arguments[i] = args[i+3];
            }
            
            CompactJVM jvm = new CompactJVM();
            jvm.loadApplication(classPath, libraryPath, mainClass, arguments);
            jvm.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

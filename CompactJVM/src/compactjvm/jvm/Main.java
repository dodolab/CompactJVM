package compactjvm.jvm;


/**
 *
 * @author Adam Vesecky
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length < 3 || args[0].equals("--help")) {
            System.out.println("Usage:\n"
                    + "parameter 1:\tPath to class root directory (including ending slash)\n"
                    + "parameter 2:\tPath to libraries root directory (including ending slash)"
                    + "parameter 3:\tNamespace of main class"
                    + "parameter 4:\tArguments (divided by spaces) of executing program"
                    + "\n"
                    + "Example: \n"
                    + "java -jar CompactJVM.jar ../SatSolver/build/classes/ ../CompactJvmLab/build/classes compactjvmlab/CompactJVMLab '../cnf1.txt ../cnf1-result.txt'");
        }
        

        
        try {            
            // here you can enable or disable logging
            
            // if TAG_INSTR is enabled, every instruction is logged !!
            //JVMLogger.enableLogging(JVMLogger.TAG_INSTR);
           // JVMLogger.enableLogging(JVMLogger.TAG_INSTR_INVOKE);
            //JVMLogger.enableLogging(JVMLogger.TAG_INSTR_JUMP);
            //JVMLogger.enableLogging(JVMLogger.TAG_INSTR_LOAD);
            //JVMLogger.enableLogging(JVMLogger.TAG_INSTR_PUSH);
            //JVMLogger.enableLogging(JVMLogger.TAG_INSTR_STORE);

            JVMLogger.enableLogging(JVMLogger.TAG_OTHER);
            //JVMLogger.enableLogging(JVMLogger.TAG_HEAP);
            JVMLogger.enableLogging(JVMLogger.TAG_GC);
            JVMLogger.enableLogging(JVMLogger.TAG_PRINT);
            
            String classPath = args[0];
            if(!classPath.endsWith("/")) classPath = classPath + "/";
            String libraryPath = args[1];
            if(!libraryPath.endsWith("/")) libraryPath = libraryPath + "/";
            String mainClass = args[2];
            String[] arguments = new String[args.length-3];
            
            System.out.println("Lib: "+libraryPath);
            
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

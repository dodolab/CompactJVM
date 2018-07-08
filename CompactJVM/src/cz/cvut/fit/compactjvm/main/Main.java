package cz.cvut.fit.compactjvm.main;

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
        
        // load testing class file
        args = new String[]{"../CompactJVMLab/build/classes/compactjvmlab/CompactJVMLab.class"};
        
        try {

            if (args.length < 1) {
                System.out.println("Missing path to class file!");
                return;
            }

            String classPath = args[0];
            CompactJVM jvm = new CompactJVM();
            jvm.loadApplication(classPath);
            jvm.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

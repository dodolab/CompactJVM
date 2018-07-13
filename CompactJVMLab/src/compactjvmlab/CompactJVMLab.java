/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compactjvmlab;

import compactjvmlab.satsolver.Clauses;
import compactjvmlab.satsolver.FileLoader;
import compactjvmlab.satsolver.SATSolver;
import compactjvmlab.satsolver.VariableEvaluation;
import java.io.IOException;

/**
 * Simple class that will be used for testing CompactJVM project
 * Just write anything, compile into class file and load it, using CompactJVM
 * @author Adam Vesecky
 */
public class CompactJVMLab {


    public static native void jvm_println(String msg);

    public static void main(/*String[] args*/) {
        
        TextWriter fileR = new TextWriter("F:\\test2.txt");
        fileR.append("Hello ");
        fileR.append("world!");
        fileR.appendLine("");
        fileR.append(12345);
        
        fileR.close();
    }

}

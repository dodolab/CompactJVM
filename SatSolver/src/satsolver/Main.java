/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satsolver;

import cz.cvut.fit.compactjvm.proxies.JVMFunctions;
import cz.cvut.fit.compactjvm.proxies.TextWriter;
import java.io.IOException;

/**
 * Simple class that will be used for testing CompactJVM project
 * Just write anything, compile into class file and load it, using CompactJVM
 * @author Adam Vesecky
 */
public class Main {


    public static void main(String[] args) throws IOException {
        
        Clauses clauses;
        int variablesCount;
        //try {
            FileLoader fileLoader = new FileLoader(args[0]);
            clauses = fileLoader.getClauses();
            variablesCount = fileLoader.getVariablesCount();
            SATSolver solver = new SATSolver();
            VariableEvaluation evaluation = solver.solve(clauses, variablesCount);
            outputResult(evaluation, args[1]);
        //} catch (IOException e) {
        //    System.out.println(e.getMessage());
        //    System.exit(1);
        //}
        

    }
    
    public static void outputResult(VariableEvaluation evaluation, String outputFile) {
        TextWriter textWriter = new TextWriter(outputFile);
        if(evaluation == null) {
            textWriter.appendLine("Není splnitelná");
        } else {
            textWriter.appendLine("Je splnitelná: ");
            textWriter.append("[");
            for(int i = 0; i < evaluation.getEvaluation().length; ++i) {
                String value = evaluation.getEvaluation()[i] == 1 ? "True" : "False";
                textWriter.append(value);
                if(i < evaluation.getEvaluation().length - 1)textWriter.append(" ");
            }
            textWriter.append("]");
        }
        textWriter.close();
    }
    
    public static int staticPublicProm;
    
    private int instantPrivateProm;
    public int instantPublicProm;
    
    public int localVariableMethod() {
        int a = 13;
        return a;
    }
    /*
    public static void staticVoidMethod(){

        fileR.close();
    }
*/
}

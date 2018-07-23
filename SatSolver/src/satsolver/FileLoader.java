/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satsolver;

import cz.cvut.fit.compactjvm.proxies.JVMFunctions;
import cz.cvut.fit.compactjvm.proxies.TextReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Nick Nemame
 */
public class FileLoader {
 
    Clauses clauses = null;
    int variablesCount;
    
    public FileLoader(String filename) throws Exception {
        
        TextReader reader = new TextReader(filename);
        int clausesCount;
        int clauseIndex = 0;
        String instanceString;
        while ((instanceString = reader.nextLine()) != null) {
            String[] tokens = instanceString.split(" ");
            if(tokens[0].equals("c")) {
                JVMFunctions.println(instanceString.substring(2));
            } else if(tokens[0].equals("p")) {
                variablesCount = JVMFunctions.parseInt(tokens[2]);
                clausesCount = JVMFunctions.parseInt(tokens[3]);
                clauses = new Clauses(clausesCount);
                //JVMFunctions.println("Hezky den");
            } else {
                //JVMFunctions.println("JAJAJA");
                //JVMFunctions.println(instanceString);
                int[] clauseLiterals = new int[tokens.length - 1];
                for(int i = 0; i < tokens.length; ++i) {
                    //JVMFunctions.println(tokens[i]);
                    int value = JVMFunctions.parseInt(tokens[i]);
                    //JVMFunctions.println(value);
                    if(value == 0) break;
                    clauseLiterals[i] = value;
                }
                Clause clause = new Clause(clauseLiterals);
                clauses.set(clauseIndex, clause);
                ++clauseIndex;
            }
        }
    }
    
    public Clauses getClauses() {
        return clauses;
    }
    
    public int getVariablesCount() {
        return variablesCount;
    }
    
}

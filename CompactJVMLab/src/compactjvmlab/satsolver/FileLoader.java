/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compactjvmlab.satsolver;

import compactjvmlab.JVMFunctions;
import compactjvmlab.TextReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author Nick Nemame
 */
public class FileLoader {
 
    Clauses clauses = null;
    int variablesCount;
    
    public FileLoader(String filename) throws FileNotFoundException, IOException {
        /*int[] i1 = new int[2];
        i1[0] = -1;
        i1[1] = 3;
        Clause c1 = new Clause(i1);
        int[] i2 = new int[3];
        i2[0] = 2;
        i2[1] = 3;
        i2[2] = -1;
        Clause c2 = new Clause(i2);
        int[] i3 = new int[3];
        i3[0] = -1;
        i3[1] = -3;
        i3[2] = -2;
        Clause c3 = new Clause(i3);
        int[] i4 = new int[3];
        i4[0] = -1;
        i4[1] = -3;
        i4[2] = 2;
        Clause c4 = new Clause(i4);
        int[] i5 = new int[3];
        i5[0] = 1;
        i5[1] = -2;
        i5[2] = -3;
        Clause c5 = new Clause(i5);
        clauses = new Clauses(5);
        clauses.set(0, c1);
        clauses.set(1, c2);
        clauses.set(2, c3);
        clauses.set(3, c4);
        clauses.set(4, c5);
        variablesCount = 3;*/
        //BufferedReader br = new BufferedReader(new FileReader(filename));
        TextReader reader = new TextReader(filename);
        int clausesCount;
        int clauseIndex = 0;
        String instanceString;
        while ((instanceString = reader.nextLine()) != null) {
            String[] tokens = instanceString.split(" ");
            if(tokens[0].equals("c")) {
                JVMFunctions.println(instanceString.substring(2));
            } else if(tokens[0].equals("p")) {
                variablesCount = IntegerX.parseInt(tokens[2]);
                clausesCount = IntegerX.parseInt(tokens[3]);
                clauses = new Clauses(clausesCount);
            } else {
                int[] clauseLiterals = new int[tokens.length - 1];
                for(int i = 0; i < tokens.length; ++i) {
                    int value = IntegerX.parseInt(tokens[i]);
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

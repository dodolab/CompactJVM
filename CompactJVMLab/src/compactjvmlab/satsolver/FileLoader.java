/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compactjvmlab.satsolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author Nick Nemame
 */
public class FileLoader {
 
    int[][] clauses = null;
    int variablesCount;
    
    public FileLoader(String filename) throws IOException {
        for(int i = 0; i < 20; ++i){System.out.println((new Date()).getTime()+"");}
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int clausesCount;
            int clauseIndex = 0;
            String instanceString;
            while ((instanceString = br.readLine()) != null) {
                String[] tokens = instanceString.split(" ");
                if("c".equals(tokens[0])) {
                    System.out.println(instanceString.substring(2));
                } else if("p".equals(tokens[0])) {
                    variablesCount = Integer.parseInt(tokens[2]);
                    clausesCount = Integer.parseInt(tokens[3]);
                    clauses = new int[clausesCount][];
                } else {
                    clauses[clauseIndex] = new int[tokens.length - 1];
                    for(int i = 0; i < tokens.length; ++i) {
                        int value = Integer.parseInt(tokens[i]);
                        if(value == 0) break;
                        clauses[clauseIndex][i] = value;
                    }
                    ++clauseIndex;
                }
            }
        }
    }
    
    public int[][] getClauses() {
        return clauses;
    }
    
    public int getVariablesCount() {
        return variablesCount;
    }
    
}

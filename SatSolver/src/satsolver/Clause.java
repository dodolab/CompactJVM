/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satsolver;

/**
 *
 * @author Nick Nemame
 */
public class Clause {
    int[] clauseLiterals;
    
    public Clause(int[] clauseLiterals) {
        this.clauseLiterals = clauseLiterals;
    }
    
    public void set(int index, int value) {
        clauseLiterals[index] = value;
    }
    
    public int get(int index) {
        return clauseLiterals[index];
    }
    
    public int size() {
        return clauseLiterals.length;
    }
}

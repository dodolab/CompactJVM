/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compactjvmlab.satsolver;

/**
 *
 * @author Nick Nemame
 */
public class Clauses {
    Clause[] clauses;

    public Clauses(int clausesCount) {
        clauses = new Clause[clausesCount];
    }
    
    public void set(int index, Clause clause) {
        clauses[index] = clause;
    }
    
    public Clause get(int index) {
        return clauses[index];
    }
    
    public int size() {
        return clauses.length;
    }
}

package satsolver;

/**
 *
 * @author Adam Vesecky
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

package satsolver;

/**
 *
 * @author Adam Vesecky
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

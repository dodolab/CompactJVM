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
public class VariableEvaluation {
    private int[] evaluation;
    private int[] evaluatedVariables;
    private int clausesCount;
    
    public VariableEvaluation(int clausesCount) {
        this.clausesCount = clausesCount;
        evaluation = new int[clausesCount];
        evaluatedVariables = new int[clausesCount];
        initializeArray(evaluation);
        initializeArray(evaluatedVariables);
    }

    public VariableEvaluation(int[] evaluation, int[] evaluatedVariables, int clausesCount) {
        this.evaluation = evaluation;
        this.evaluatedVariables = evaluatedVariables;
        this.clausesCount = clausesCount;
    }
    
    /**
     * 
     * @param variable
     * @param value
     * @return 
     */
    public VariableEvaluation getNewEvaluation(int variable, int value) {
        VariableEvaluation clone = getClone();
        clone.evaluateVariable(variable, value);
        return clone;
    }
    
    public void evaluateVariable(int variable, int value) {
        evaluation[variable] = value;
        evaluatedVariables[variable] = 1;
        
    }

    public int[] getEvaluation() {
        return evaluation;
    }

    public int[] getEvaluatedVariables() {
        return evaluatedVariables;
    }

    public int getClausesCount() {
        return clausesCount;
    }
    
    public String toString() {
        String str = "";
        for(int i = 0; i < evaluation.length; ++i) {
            if(str != "") str += ", ";
            str += evaluation[i] == 1 ? "True" : "False";
        }
        return "[" + str + "]";
    }
    
    private VariableEvaluation getClone() {
        int[] clonedEvaluation = cloneArray(evaluation);
        int[] clonedEvaluatedClauses = cloneArray(evaluatedVariables);
        VariableEvaluation clone = new VariableEvaluation(clonedEvaluation, clonedEvaluatedClauses, clausesCount);
        return clone;
    }
    
    private int[] cloneArray(int[] array) {
        int[] clone = new int[array.length];
        for(int i = 0; i < array.length; i++) {
            clone[i] = array[i];
        }
        return clone;
    }
    
    private void initializeArray(int[] array) {
        for(int i = 0; i < array.length; i++) {
            array[i] = 0;
        }
    }
    
    
}

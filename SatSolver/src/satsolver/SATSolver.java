/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satsolver;

import java.util.Date;

/**
 *
 * @author Nick Nemame
 */
public class SATSolver {
    
    public static final int EVALUATE_NONE = -1;
    public static final int EVALUATE_FALSE = 0;
    public static final int EVALUATE_TRUE = 1;
    public static final int EVALUATE_BOTH = 2;
    
    int nextVariable;
    int nextVariableEvaluation;
    
    Clauses clauses;
    int variablesCount;
    /**
     * @param clauses
     * @param variablesCount
     * @return 
     */
    public VariableEvaluation solve(Clauses clauses, int variablesCount) {
        this.clauses = clauses;
        this.variablesCount = variablesCount;
        
        VariableEvaluation evaluation = new VariableEvaluation(variablesCount);
        
        return solveBranch(evaluation);
    }
    
    public VariableEvaluation solveBranch(VariableEvaluation evaluation) {
        prepareNextVariable(evaluation);
        
        if(nextVariable == EVALUATE_NONE) {
            return isSatisfied(evaluation) == 1 ? evaluation : null;
        } else if(nextVariableEvaluation == EVALUATE_TRUE || nextVariableEvaluation == EVALUATE_FALSE) {
            evaluation.evaluateVariable(nextVariable, nextVariableEvaluation);
            return solveBranch(evaluation);
        } else if(nextVariableEvaluation == EVALUATE_BOTH) {
            VariableEvaluation evaluation2 = evaluation.getNewEvaluation(nextVariable, EVALUATE_FALSE);
            evaluation.evaluateVariable(nextVariable, EVALUATE_TRUE);
            VariableEvaluation result;
            result = solveBranch(evaluation);
            if(result != null) return result;
            return solveBranch(evaluation2);
        }
        return null;
    }
    
    private void prepareNextVariable(VariableEvaluation currentEvaluation) {
        int[] evaluatedVariables = currentEvaluation.getEvaluatedVariables();
        for(int i = 0; i < evaluatedVariables.length; ++i){
            if(evaluatedVariables[i] == 0) {
                nextVariableEvaluation = EVALUATE_BOTH;
                nextVariable = i;
                return;
            }
        }
        nextVariableEvaluation = EVALUATE_NONE;
        nextVariable = EVALUATE_NONE;
    }
    
    private int isSatisfied(VariableEvaluation evaluation) {
        for(int i = 0; i < clauses.size(); ++i) {
            if(isClauseSatisfied(clauses.get(i), evaluation) == 0) {
                return 0;
            }
        }
        return 1;
    }
    
    private int isClauseSatisfied(Clause clause, VariableEvaluation evaluation) {
        int[] evalArray = evaluation.getEvaluation();
        int trueCount = 0;
        for(int i = 0; i < clause.size(); ++i) {
            int literal = clause.get(i);
            int isPositive = literal > 0 ? 1 : 0;
            int literalIndex = Math.abs(literal) - 1;
            int value = evalArray[literalIndex];
            if(isPositive == 0) {
                value = value == 0 ? 1 : 0;
            }
            trueCount += value;
        }
        return trueCount > 0 ? 1 : 0;
    }
    
}

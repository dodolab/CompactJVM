package satsolver;

import compactjvm.proxy.JVMFunctions;
import compactjvm.proxy.TextWriter;

/**
 * SATSolver for boolean satisfiability
 *
 * @author Adam Vesecky
 */
public class Main {

    public static void main(String[] args) {
        Clauses clauses;
        int variablesCount;
        FileLoader fileLoader = null;
        try {
            fileLoader = new FileLoader(args[0]);
            clauses = fileLoader.getClauses();
            variablesCount = fileLoader.getVariablesCount();
        } catch (Exception e) {
            JVMFunctions.println("Unable to load source file");
            return;
        }
        SATSolver solver = new SATSolver();
        VariableEvaluation evaluation = solver.solve(clauses, variablesCount);
        outputResult(evaluation, args[1]);

    }

    public static void outputResult(VariableEvaluation evaluation, String outputFile) {
        TextWriter textWriter = new TextWriter(outputFile);
        if (evaluation == null) {
            textWriter.appendLine("Is not satisfiable");
        } else {
            textWriter.appendLine("Is satisfiable: ");
            textWriter.append("[");
            for (int i = 0; i < evaluation.getEvaluation().length; ++i) {
                String value = evaluation.getEvaluation()[i] == 1 ? "True" : "False";
                textWriter.append(value);
                if (i < evaluation.getEvaluation().length - 1) {
                    textWriter.append(" ");
                }
            }
            textWriter.append("]");
        }
        textWriter.close();
    }
}

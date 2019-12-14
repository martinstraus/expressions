/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator.string;

import expressions.evaluator.BinaryFunction;
import expressions.evaluator.EvaluationException;
import expressions.evaluator.SymbolsTable;
import java.util.List;

/**
 *
 * @author martinstraus
 */
public class Matches extends BinaryFunction<Boolean> {

    public Matches() {
        super("matches");
    }

    @Override
    public Boolean evaluate(SymbolsTable symbolsTable) {
        String valueToTest = a(symbolsTable, String.class);
        String regexp = b(symbolsTable, String.class);
        return valueToTest.matches(regexp);
    }

}

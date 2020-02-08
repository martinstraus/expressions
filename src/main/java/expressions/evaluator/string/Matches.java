/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator.string;

import expressions.evaluator.BinaryFunction;
import expressions.evaluator.SymbolsTable;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class Matches extends BinaryFunction<Boolean> {

    private static final String REGEXP = "regexp";
    private static final String VALUE = "value";

    public Matches() {
        super("matches", VALUE, REGEXP);
    }

    @Override
    public Boolean evaluate(SymbolsTable symbolsTable) {
        String valueToTest = valueOrException(symbolsTable, VALUE, String.class);
        String regexp = valueOrException(symbolsTable, REGEXP, String.class);
        return valueToTest.matches(regexp);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator.string;

import expressions.evaluator.BinaryFunction;
import expressions.evaluator.SymbolsTable;
import static java.util.Arrays.asList;
import java.util.List;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class Split extends BinaryFunction<List<String>> {

    private static final String REGEXP = "regexp";
    private static final String VALUE = "value";

    public Split() {
        super("split", VALUE, REGEXP);
    }

    @Override
    public List<String> evaluate(SymbolsTable symbolsTable) {
        String valueToSplit = valueOrException(symbolsTable, VALUE, String.class);
        String regexp = valueOrException(symbolsTable, REGEXP, String.class);
        return asList(valueToSplit.split(regexp));
    }
}

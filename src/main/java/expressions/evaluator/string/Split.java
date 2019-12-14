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
 * @author martinstraus
 */
public class Split extends BinaryFunction<List<String>> {

    public Split() {
        super("split");
    }

    @Override
    public List<String> evaluate(SymbolsTable symbolsTable) {
        String valueToSplit = a(symbolsTable, String.class);
        String regexp = b(symbolsTable, String.class);
        return asList(valueToSplit.split(regexp));
    }
}

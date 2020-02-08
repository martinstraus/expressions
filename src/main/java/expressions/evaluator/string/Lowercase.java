/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator.string;

import expressions.evaluator.EvaluationException;
import expressions.evaluator.SymbolsTable;
import expressions.evaluator.UnaryFunction;
import java.util.List;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class Lowercase extends UnaryFunction<String> {

    public Lowercase() {
        super("lowercase");
    }

    @Override
    public String evaluate(SymbolsTable symbolsTable) {
        return valueOrException(symbolsTable, String.class).toLowerCase();
    }

}

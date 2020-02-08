/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator.string;

import expressions.evaluator.SymbolsTable;
import expressions.evaluator.UnaryFunction;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class Uppercase extends UnaryFunction<String> {

    public Uppercase() {
        super("uppercase");
    }

    @Override
    public String evaluate(SymbolsTable symbolsTable) {
        return valueOrException(symbolsTable, String.class).toUpperCase();
    }

}

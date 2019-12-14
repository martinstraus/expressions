/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator.string;

import expressions.evaluator.SymbolsTable;
import expressions.evaluator.UnaryFunction;
import java.math.BigDecimal;

/**
 *
 * @author martinstraus
 */
public class Length extends UnaryFunction<BigDecimal> {

    public Length() {
        super("length");
    }

    @Override
    public BigDecimal evaluate(SymbolsTable symbolsTable) {
        return new BigDecimal(valueOrException(symbolsTable, String.class).length());
    }
}

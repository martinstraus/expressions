/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import expressions.evaluator.SymbolsTable;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author martinstraus
 */
public class Negate implements Expression<BigDecimal> {

    private final Expression<BigDecimal> value;

    public Negate(Expression<BigDecimal> value) {
        this.value = value;
    }

    @Override
    public BigDecimal evaluate(SymbolsTable symbolsTable) {
        return value.evaluate(symbolsTable).negate();
    }

    @Override
    public Type type() {
        return Types.NUMBER;
    }

}

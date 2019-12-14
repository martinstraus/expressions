/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator.date;

import expressions.evaluator.BinaryFunction;
import expressions.evaluator.SymbolsTable;
import java.math.BigDecimal;

/**
 *
 * @author martinstraus
 */
public class Period extends BinaryFunction<java.time.Period> {

    public Period() {
        super("period");
    }

    @Override
    public java.time.Period evaluate(SymbolsTable symbolsTable) {
        BigDecimal amount = a(symbolsTable, BigDecimal.class);
        Unit unit = b(symbolsTable,Unit.class);
        return unit.period(amount);
    }

}

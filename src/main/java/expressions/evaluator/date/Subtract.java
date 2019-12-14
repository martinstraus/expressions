/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator.date;

import expressions.evaluator.BinaryFunction;
import expressions.evaluator.SymbolsTable;
import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author martinstraus
 */
public class Subtract extends BinaryFunction<LocalDate> {

    public Subtract() {
        super("subtract");
    }

    @Override
    public LocalDate evaluate(SymbolsTable symbolsTable) {
        LocalDate date = a(symbolsTable, LocalDate.class);
        Period period = b(symbolsTable, Period.class);
        return date.minus(period);
    }

}

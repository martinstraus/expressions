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

    private static final String PERIOD = "period";
    private static final String DATE = "date";

    public Subtract() {
        super("subtract", DATE, PERIOD);
    }

    @Override
    public LocalDate evaluate(SymbolsTable symbolsTable) {
        LocalDate date = valueOrException(symbolsTable, DATE, LocalDate.class);
        Period period = valueOrException(symbolsTable, PERIOD, Period.class);
        return date.minus(period);
    }

}

package expressions.evaluator.date;

import expressions.evaluator.BinaryFunction;
import expressions.evaluator.SymbolsTable;
import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class Add extends BinaryFunction<LocalDate> {
    private static final String PERIOD = "period";
    private static final String DATE = "date";

    public Add() {
        super("add", DATE, PERIOD);
    }

    @Override
    public LocalDate evaluate(SymbolsTable symbolsTable) {
        LocalDate date = valueOrException(symbolsTable, DATE, LocalDate.class);
        Period period = valueOrException(symbolsTable, PERIOD, Period.class);
        return date.plus(period);
    }
}

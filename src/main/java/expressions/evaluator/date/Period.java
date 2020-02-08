package expressions.evaluator.date;

import expressions.evaluator.BinaryFunction;
import expressions.evaluator.SymbolsTable;
import java.math.BigDecimal;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class Period extends BinaryFunction<java.time.Period> {

    private static final String UNIT = "unit";
    private static final String AMOUNT = "amount";

    public Period() {
        super("period", AMOUNT, UNIT);
    }

    @Override
    public java.time.Period evaluate(SymbolsTable symbolsTable) {
        BigDecimal amount = valueOrException(symbolsTable, AMOUNT, BigDecimal.class);
        Unit unit = valueOrException(symbolsTable, UNIT, Unit.class);
        return unit.period(amount);
    }

}

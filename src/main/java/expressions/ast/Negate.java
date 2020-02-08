package expressions.ast;

import expressions.evaluator.SymbolsTable;
import java.math.BigDecimal;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
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

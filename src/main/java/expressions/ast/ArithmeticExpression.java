package expressions.ast;

import expressions.evaluator.SymbolsTable;
import java.math.BigDecimal;
import java.util.function.BiFunction;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class ArithmeticExpression implements Expression<BigDecimal> {

    private final BiFunction<BigDecimal, BigDecimal, BigDecimal> function;
    private final Expression<BigDecimal> left;
    private final Expression<BigDecimal> right;

    public ArithmeticExpression(BiFunction<BigDecimal, BigDecimal, BigDecimal> function, Expression<BigDecimal> left, Expression<BigDecimal> right) {
        this.left = left;
        this.right = right;
        this.function = function;
    }

    @Override
    public BigDecimal evaluate(SymbolsTable symbolsTable) {
        return function.apply(left.evaluate(symbolsTable), right.evaluate(symbolsTable));
    }

    @Override
    public Type type() {
        return Types.NUMBER;
    }

}

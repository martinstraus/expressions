package expressions.ast;

import expressions.evaluator.EvaluationException;
import expressions.evaluator.SymbolsTable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.function.BiFunction;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class BooleanComparison implements Expression<Boolean> {

    private final BiFunction<BigDecimal, BigDecimal, Boolean> comparator;
    private final Expression<?> left;
    private final Expression<?> right;

    public BooleanComparison(BiFunction<BigDecimal, BigDecimal, Boolean> comparator, Expression<?> left, Expression<?> right) {
        this.comparator = comparator;
        this.left = left;
        this.right = right;
    }

    @Override
    public Type type() {
        return Types.BOOLEAN;
    }

    @Override
    public Boolean evaluate(SymbolsTable symbolsTable) {
        Object leftValue = left.evaluate(symbolsTable);
        Object rightValue = right.evaluate(symbolsTable);
        if (!Types.ofClass(BigDecimal.class, leftValue, rightValue)) {
            throw new EvaluationException(
                    String.format("Expected number operands. Got %s and %s", left.type(), right.type())
            );
        }
        return comparator.apply((BigDecimal) leftValue, (BigDecimal) rightValue);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import expressions.EvaluationException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.function.BiFunction;

/**
 *
 * @author martinstraus
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
    public Boolean evaluate(Map<String, Object> context) {
        Object leftValue = left.evaluate(context);
        Object rightValue = right.evaluate(context);
        if (!Types.ofClass(BigDecimal.class, leftValue, rightValue)) {
            throw new EvaluationException(
                    String.format("Expected number operands. Got %s and %s", left.type(), right.type())
            );
        }
        return comparator.apply((BigDecimal) leftValue, (BigDecimal) rightValue);
    }

}

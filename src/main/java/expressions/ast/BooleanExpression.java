/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import java.util.Map;
import java.util.function.BiFunction;

/**
 *
 * @author martinstraus
 */
public class BooleanExpression<T> implements Expression<Boolean> {

    private final BiFunction<T, T, Boolean> comparator;
    private final Expression<T> left;
    private final Expression<T> right;

    public BooleanExpression(BiFunction<T, T, Boolean> comparator, Expression<T> left, Expression<T> right) {
        this.comparator = comparator;
        this.left = left;
        this.right = right;
    }

    @Override
    public Boolean evaluate(Map<String, Object> context) {
        return comparator.apply(left.evaluate(context), right.evaluate(context));
    }

    @Override
    public Type type() {
        return Types.BOOLEAN;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 *
 * @author martinstraus
 */
public class ArithmeticExpression implements Expression<BigDecimal> {

    private final Expression<BigDecimal> left;
    private final Expression<BigDecimal> right;
    private final BiFunction<BigDecimal, BigDecimal, BigDecimal> function;

    public ArithmeticExpression(Expression<BigDecimal> left, Expression<BigDecimal> right, BiFunction<BigDecimal, BigDecimal, BigDecimal> function) {
        this.left = left;
        this.right = right;
        this.function = function;
    }

    @Override
    public BigDecimal evaluate(Map<String, Object> context) {
        return function.apply(left.evaluate(context), right.evaluate(context));
    }

}

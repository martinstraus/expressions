/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.BiFunction;

/**
 *
 * @author martinstraus
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
    public BigDecimal evaluate(Map<String, Object> context) {
        return function.apply(left.evaluate(context), right.evaluate(context));
    }

}

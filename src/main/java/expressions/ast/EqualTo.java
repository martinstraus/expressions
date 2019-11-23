/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author martinstraus
 */
public class EqualTo implements Expression<Boolean> {

    private final Expression<BigDecimal> left;
    private final Expression<BigDecimal> right;

    public EqualTo(Expression<BigDecimal> left, Expression<BigDecimal> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Boolean evaluate(Map<String, Object> context) {
        return left.evaluate(context).compareTo(right.evaluate(context)) == 0;
    }

}

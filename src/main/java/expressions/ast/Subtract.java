/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import java.math.BigDecimal;

/**
 *
 * @author martinstraus
 */
public class Subtract extends ArithmeticExpression {

    public Subtract(Expression<BigDecimal> left, Expression<BigDecimal> right) {
        super(left, right, (l, r) -> l.subtract(r));
    }

}

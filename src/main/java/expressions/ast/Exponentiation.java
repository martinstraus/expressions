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
public class Exponentiation implements Expression<BigDecimal> {

    private final Expression<BigDecimal> base;
    private final Expression<BigDecimal> exponent;

    public Exponentiation(Expression<BigDecimal> base, Expression<BigDecimal> exponent) {
        this.base = base;
        this.exponent = exponent;
    }

    @Override
    public BigDecimal evaluate(Map<String, Object> context) {
        return new BigDecimal(Math.pow(base.evaluate(context).doubleValue(), exponent.evaluate(context).doubleValue()));
    }

}

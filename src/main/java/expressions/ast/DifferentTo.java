/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import java.util.Map;

/**
 *
 * @author martinstraus
 */
public class DifferentTo implements Expression<Boolean> {

    private final EqualTo equals;

    public DifferentTo(Expression left, Expression right) {
        equals = new EqualTo(left, right);
    }

    @Override
    public Boolean evaluate(Map<String, Object> context) {
        return !equals.evaluate(context);
    }

}

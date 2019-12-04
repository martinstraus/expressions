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
public class Not implements Expression<Boolean> {

    private final Expression<Boolean> value;

    public Not(Expression<Boolean> value) {
        this.value = value;
    }

    @Override
    public Boolean evaluate(Map<String, Object> context) {
        return !value.evaluate(context);
    }

    @Override
    public Type type() {
        return Types.BOOLEAN;
    }
}

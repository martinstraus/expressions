/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import java.util.Collection;
import java.util.Map;

/**
 *
 * @author martinstraus
 */
public class In implements Expression<Boolean> {

    private final Expression value;
    private final Expression<Collection> set;

    public In(Expression value, Expression<Collection> set) {
        this.value = value;
        this.set = set;
    }

    @Override
    public Boolean evaluate(Map<String, Object> context) {
        return set.evaluate(context).contains(value.evaluate(context));
    }

    @Override
    public Type type() {
        return Types.BOOLEAN;
    }
}

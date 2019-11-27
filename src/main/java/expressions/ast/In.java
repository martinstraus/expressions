/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author martinstraus
 */
public class In implements Expression<Boolean> {

    private final Expression value;
    private final Expression<java.util.Set> set;

    public In(Expression value, Expression<Set> set) {
        this.value = value;
        this.set = set;
    }

    @Override
    public Boolean evaluate(Map<String, Object> context) {
        return set.evaluate(context).contains(value.evaluate(context));
    }

}

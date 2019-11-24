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
 * @param <T>
 */
public class Variable<T> implements Expression<T> {

    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public T evaluate(Map<String, Object> context) {
        return (T) context.get(name);
    }

}

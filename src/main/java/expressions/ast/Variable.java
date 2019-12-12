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

    private final Type type;
    private final String name;

    public Variable(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public T evaluate(Map<String, Object> context) {
        return (T) context.get(name);
    }

    @Override
    public Type type() {
        return type;
    }

    public String name() {
        return name;
    }
}

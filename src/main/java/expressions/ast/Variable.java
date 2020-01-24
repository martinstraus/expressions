/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import expressions.evaluator.EvaluationException;
import expressions.evaluator.SymbolsTable;

import java.util.*;

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
    public T evaluate(SymbolsTable symbolsTable) {
        return (T) symbolsTable
                .value(name)
                .orElseThrow(() -> new EvaluationException(String.format("Could not find variable %s.", name)));
    }

    @Override
    public Type type() {
        return type;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable<?> variable = (Variable<?>) o;
        return type.equals(variable.type) &&
                name.equals(variable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name);
    }
}

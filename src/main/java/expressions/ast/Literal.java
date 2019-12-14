/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import expressions.evaluator.SymbolsTable;
import java.util.Map;

/**
 *
 * @author martinstraus
 */
public class Literal<T> implements Expression<T> {

    private final Type type;
    private final T value;

    public Literal(Type type, T value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public T evaluate(SymbolsTable symbolsTable) {
        return value;
    }

    @Override
    public Type type() {
        return type;
    }

}

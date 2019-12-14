/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import expressions.evaluator.SymbolsTable;
import java.util.Map;
import static java.util.stream.Collectors.toSet;

/**
 *
 * @author martinstraus
 */
public class Set<T> implements Expression<java.util.Set<T>> {

    private final java.util.Set<Expression<T>> values;

    public Set(java.util.Set<Expression<T>> values) {
        this.values = values;
    }

    @Override
    public java.util.Set<T> evaluate(SymbolsTable symbolsTable) {
        return values.stream().map((e) -> e.evaluate(symbolsTable)).collect(toSet());
    }

    @Override
    public Type type() {
        return Types.SET;
    }

    
}

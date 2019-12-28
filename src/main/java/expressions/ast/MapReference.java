/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import expressions.evaluator.SymbolsTable;

/**
 *
 * @author martinstraus
 */
public class MapReference<T> implements Expression<T> {
    
    private final Expression<java.util.Map> map;
    private final Expression key;

    public MapReference(Expression<java.util.Map> map, Expression key) {
        this.map = map;
        this.key = key;
    }

    @Override
    public Type type() {
        return Types.UNKNOWN;
    }

    @Override
    public T evaluate(SymbolsTable symbolsTable) {
        return (T) map.evaluate(symbolsTable).get(key.evaluate(symbolsTable));
    }
    
}

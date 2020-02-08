package expressions.ast;

import expressions.evaluator.SymbolsTable;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
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

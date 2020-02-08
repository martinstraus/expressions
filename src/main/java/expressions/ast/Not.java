package expressions.ast;

import expressions.evaluator.SymbolsTable;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class Not implements Expression<Boolean> {

    private final Expression<Boolean> value;

    public Not(Expression<Boolean> value) {
        this.value = value;
    }

    @Override
    public Boolean evaluate(SymbolsTable symbolsTable) {
        return !value.evaluate(symbolsTable);
    }

    @Override
    public Type type() {
        return Types.BOOLEAN;
    }
}

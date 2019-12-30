package expressions.ast;

import expressions.evaluator.SymbolsTable;

public class DefineValue<T> implements Statement {

    private final String name;
    private final Expression<T> value;

    public DefineValue(String name, Expression<T> value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void execute(SymbolsTable symbolsTable) {
        symbolsTable.put(name, value.evaluate(symbolsTable));
    }
}

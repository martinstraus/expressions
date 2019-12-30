package expressions.ast;

import expressions.evaluator.SymbolsTable;

public interface Statement {

    void execute(SymbolsTable symbolsTable);
}

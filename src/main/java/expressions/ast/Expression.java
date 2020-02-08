package expressions.ast;

import expressions.evaluator.SymbolsTable;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public interface Expression<T> {
    
    Type type();
    
    T evaluate(SymbolsTable symbolsTable);
}

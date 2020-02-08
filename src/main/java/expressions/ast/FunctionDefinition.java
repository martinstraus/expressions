package expressions.ast;

import expressions.evaluator.SymbolsTable;
import java.util.List;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public interface FunctionDefinition<T> {
    
    String name();
    
    List<ParameterDefinition> parameters();

    T evaluate(SymbolsTable symbolsTable);
    
}

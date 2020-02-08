package expressions.ast;

import expressions.evaluator.SymbolsTable;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class Program {

    private final List<FunctionDefinition> functions;
    private final List<Statement> statements;
    private final Expression expression;

    public Program(List<FunctionDefinition> functions, List<Statement> statements, Expression expression) {
        this.functions = functions;
        this.statements = statements;
        this.expression = expression;
    }
    
    public List<FunctionDefinition> functions() {
        return Collections.unmodifiableList(functions);
    }

    public Object evaluate(SymbolsTable symbolsTable) {
        statements.forEach((statement) -> statement.execute(symbolsTable));
        return expression.evaluate(symbolsTable);
    }

}

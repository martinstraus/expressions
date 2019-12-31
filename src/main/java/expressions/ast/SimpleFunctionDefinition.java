/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import expressions.evaluator.SymbolsTable;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author martinstraus
 */
public class SimpleFunctionDefinition<T> implements FunctionDefinition<T> {

    private final String name;
    private final List<ParameterDefinition> parameters;
    private final List<Statement> statements;
    private final Expression<T> expression;

    public SimpleFunctionDefinition(String name, List<ParameterDefinition> parameters, List<Statement> statements, Expression<T> expression) {
        this.name = name;
        this.parameters = parameters;
        this.statements = statements;
        this.expression = expression;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public List<ParameterDefinition> parameters() {
        return Collections.unmodifiableList(parameters);
    }

    @Override
    public T evaluate(SymbolsTable symbolsTable) {
        statements.forEach((statement) -> statement.execute(symbolsTable));
        return expression.evaluate(symbolsTable);
    }


}

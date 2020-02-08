package expressions.ast;

import expressions.evaluator.EvaluationException;
import expressions.evaluator.SymbolsTable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class FunctionCall<T> implements Expression<T> {

    private final String name;
    private final List<Expression> parameters;

    public FunctionCall(String name, List<Expression> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public T evaluate(SymbolsTable symbolsTable) {
        FunctionDefinition<T> function = symbolsTable
                .function(name)
                .orElseThrow(() -> new EvaluationException(String.format("Function %s not defined.", name)));
        return (T) function.evaluate(newSymbolsTable(function, symbolsTable));
    }

    private SymbolsTable newSymbolsTable(FunctionDefinition function, SymbolsTable symbolsTable) {
        List<ParameterDefinition> parameters = function.parameters();
        Map<String, Object> values = new HashMap<>();
        for (int i = 0; i < parameters.size(); i++) {
            values.put(parameters.get(i).name(), this.parameters.get(i).evaluate(symbolsTable));
        }
        SymbolsTable newSymbolsTable = symbolsTable.push();
        newSymbolsTable.putValues(values);
        return newSymbolsTable;
    }

    @Override
    public Type type() {
        return Types.UNKNOWN;
    }

}

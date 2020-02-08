package expressions.evaluator.collections;

import expressions.ast.FunctionDefinition;
import expressions.ast.ParameterDefinition;
import expressions.evaluator.EvaluationException;
import expressions.evaluator.SymbolsTable;
import static java.util.Arrays.asList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class In implements FunctionDefinition<Boolean> {

    private final ParameterDefinition valueParameter = new ParameterDefinition("value");
    private final ParameterDefinition collectionParameter = new ParameterDefinition("collection");

    @Override
    public String name() {
        return "in";
    }

    @Override
    public List<ParameterDefinition> parameters() {
        return asList(valueParameter, collectionParameter);
    }

    @Override
    public Boolean evaluate(SymbolsTable symbolsTable) {
        Object value = parameterValue(symbolsTable, valueParameter);
        Collection collection = (Collection) parameterValue(symbolsTable, collectionParameter);
        return collection.contains(value);
    }

    private Object parameterValue(SymbolsTable symbolsTable, ParameterDefinition parameter) {
        return symbolsTable
            .value(parameter.name())
            .orElseThrow(() -> new EvaluationException(String.format("Parameter %s not found.", parameter.name())));
    }

}

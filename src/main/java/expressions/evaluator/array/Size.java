package expressions.evaluator.array;

import expressions.ast.FunctionDefinition;
import expressions.ast.ParameterDefinition;
import expressions.evaluator.EvaluationException;
import expressions.evaluator.SymbolsTable;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;

public class Size implements FunctionDefinition<BigDecimal> {

    private final ParameterDefinition parameter = new ParameterDefinition("collection");
    private final List<ParameterDefinition> parameters = asList(parameter);

    @Override
    public String name() {
        return "size";
    }

    @Override
    public List<ParameterDefinition> parameters() {
        return parameters;
    }

    @Override
    public BigDecimal evaluate(SymbolsTable symbolsTable) {
        return new BigDecimal(parameterValue(symbolsTable).size());
    }

    private Collection parameterValue(SymbolsTable symbolsTable) {
        return (Collection) symbolsTable
                .value(parameter.name())
                .orElseThrow(()-> new EvaluationException(String.format("Parameter %s not found.",parameter.name())));
    }
}

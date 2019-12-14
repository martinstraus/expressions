/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator;

import expressions.ast.FunctionDefinition;
import expressions.ast.ParameterDefinition;
import static java.util.Arrays.asList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author martinstraus
 */
public abstract class BinaryFunction<T> implements FunctionDefinition<T> {

    private final String name;
    private List<ParameterDefinition> parameters = asList(
            new ParameterDefinition("a"),
            new ParameterDefinition("b")
    );

    public BinaryFunction(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public List<ParameterDefinition> parameters() {
        return Collections.unmodifiableList(parameters);
    }

    protected <T> T a(SymbolsTable symbolsTable, Class<T> type) {
        return valueOrException(symbolsTable, "a", type);
    }

    protected <T> T b(SymbolsTable symbolsTable, Class<T> type) {
        return valueOrException(symbolsTable, "b", type);
    }

    protected <T> T valueOrException(SymbolsTable symbolsTable, String name, Class<T> expectedType) {
        Optional value = symbolsTable.value(name);
        if (!value.isPresent()) {
            throw new EvaluationException(
                    String.format(
                            "'%s' expects the parameter '%s' of type '%s'.",
                            this.name,
                            name,
                            expectedType.getSimpleName()
                    )
            );
        }
        if (!(expectedType.isAssignableFrom(value.get().getClass()))) {
            throw new EvaluationException(
                    String.format(
                            "'%s' expects the parameter '%s' of type '%s' but was of type %s.",
                            this.name,
                            name,
                            expectedType.getSimpleName(),
                            value.get().getClass().getSimpleName()
                    )
            );
        }
        return (T) value.get();
    }

}

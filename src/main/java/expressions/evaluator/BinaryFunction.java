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
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public abstract class BinaryFunction<T> implements FunctionDefinition<T> {

    private final String name;
    private final List<ParameterDefinition> parameters;

    public BinaryFunction(String name, String param1, String param2) {
        this.name = name;
        parameters = asList(new ParameterDefinition(param1), new ParameterDefinition(param2));
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public List<ParameterDefinition> parameters() {
        return Collections.unmodifiableList(parameters);
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

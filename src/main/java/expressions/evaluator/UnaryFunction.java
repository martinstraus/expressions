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
public abstract class UnaryFunction<T> implements FunctionDefinition<T> {

    private final String name;
    private List<ParameterDefinition> parameters = asList(new ParameterDefinition("value"));

    public UnaryFunction(String name) {
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

    protected <T> T valueOrException(SymbolsTable symbolsTable, Class<T> expectedType) {
        Optional value = symbolsTable.value("value");
        if (!value.isPresent() || !(value.get() instanceof String)) {
            throw new EvaluationException(String.format("\"%s\" expects 1 parameter of type string.", name));
        }
        return (T) value.get();
    }

}

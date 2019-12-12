/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author martinstraus
 */
public class SimpleFunctionDefinition<T> implements FunctionDefinition<T> {

    private final String name;
    private final List<ParameterDefinition> parameters;
    private final Expression<T> expression;

    public SimpleFunctionDefinition(String name, List<ParameterDefinition> parameters, Expression<T> expression) {
        this.name = name;
        this.parameters = parameters;
        this.expression = expression;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public T evaluate(Map<String, Object> context, List<Object> parametersValues) {
        return expression.evaluate(newContext(context, parametersValues));
    }

    private Map<String, Object> newContext(Map<String, Object> context, List<Object> parametersValues) {
        Map<String, Object> newContext = new HashMap<>();
        newContext.putAll(context);
        int i = 0;
        for (ParameterDefinition parameter : this.parameters) {
            newContext.put(parameter.name(), parametersValues.get(i));
            i++;
        }
        return newContext;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author martinstraus
 */
public class FunctionCall<T> implements Expression<T> {

    private final String name;
    private final List<Expression> parameters;
    private final Map<String, FunctionDefinition> functions;

    public FunctionCall(String name, List<Expression> parameters, Map<String, FunctionDefinition> functions) {
        this.name = name;
        this.parameters = parameters;
        this.functions = functions;
    }

    @Override
    public T evaluate(Map<String, Object> context) {
        return (T) functions.get(name).evaluate(context, evaluatedParameters(context));
    }

    private List<Object> evaluatedParameters(Map<String, Object> context) {
        return parameters.stream().map((p) -> p.evaluate(context)).collect(toList());
    }

    @Override
    public Type type() {
        return Types.UNKNOWN;
    }

}

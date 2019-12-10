/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator;

import expressions.ast.FunctionDefinition;
import java.util.List;
import java.util.Map;

/**
 *
 * @author martinstraus
 */
public class Lowercase implements FunctionDefinition<String> {

    @Override
    public String evaluate(Map<String, Object> context, List<Object> parametersValues) {
        if (parametersValues.size() != 1 || !(parametersValues.get(0) instanceof String)) {
            throw new EvaluationException("'lowercase' expects 1 parameter of type string.");
        }
        return ((String) parametersValues.get(0)).toLowerCase();
    }

}

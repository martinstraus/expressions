/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator;

import expressions.ast.Expression;
import expressions.ast.FunctionDefinition;
import java.util.List;
import java.util.Map;

/**
 *
 * @author martinstraus
 */
public class Matches implements FunctionDefinition<Boolean> {

    @Override
    public Boolean evaluate(Map<String, Object> context, List<Object> parametersValues) {
        if (parametersValues.size() != 2) {
            throw new EvaluationException("\"contains\" expects two parameters");
        }
        String valueToTest = (String) parametersValues.get(0);
        String regexp = (String) parametersValues.get(1);
        return valueToTest.matches(regexp);
    }

}

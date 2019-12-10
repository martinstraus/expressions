/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator.string;

import expressions.ast.FunctionDefinition;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author martinstraus
 */
public class Split implements FunctionDefinition<List<String>> {

    @Override
    public List<String> evaluate(Map<String, Object> context, List<Object> parametersValues) {
        String valueToSplit = (String) parametersValues.get(0);
        String regexp = (String) parametersValues.get(1);
        return asList(valueToSplit.split(regexp));
    }

}

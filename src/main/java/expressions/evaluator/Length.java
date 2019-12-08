/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator;

import expressions.ast.FunctionDefinition;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author martinstraus
 */
public class Length implements FunctionDefinition<BigDecimal> {

    @Override
    public BigDecimal evaluate(Map<String, Object> context, List<Object> parametersValues) {
        return new BigDecimal(((String) parametersValues.get(0)).length());
    }

}

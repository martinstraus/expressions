/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator.date;

import expressions.ast.FunctionDefinition;
import expressions.evaluator.EvaluationException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author martinstraus
 */
public class Period implements FunctionDefinition<java.time.Period> {

    @Override
    public java.time.Period evaluate(Map<String, Object> context, List<Object> parametersValues) {
        if (parametersValues.size() != 2
                || !(parametersValues.get(0) instanceof BigDecimal)
                || !(parametersValues.get(1) instanceof Unit)) {
            throw new EvaluationException("'period' expects parameters (amount,unit).");
        }
        BigDecimal amount = (BigDecimal) parametersValues.get(0);
        Unit unit = (Unit) parametersValues.get(1);
        return unit.period(amount);
    }

    @Override
    public String name() {
        return "period";
    }

}

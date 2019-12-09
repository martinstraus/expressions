/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator;

import expressions.EvaluationException;
import expressions.ast.FunctionDefinition;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;

/**
 *
 * @author martinstraus
 */
public class DateSubtract implements FunctionDefinition<LocalDate> {

    @Override
    public LocalDate evaluate(Map<String, Object> context, List<Object> parametersValues) {
        if (parametersValues.size() != 2
                || !(parametersValues.get(0) instanceof LocalDate)
                || !(parametersValues.get(1) instanceof Period)) {
            throw new EvaluationException("Function 'add' expects parameters of types (date,period).");
        }
        LocalDate date = (LocalDate) parametersValues.get(0);
        Period period = (Period) parametersValues.get(1);
        return date.minus(period);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator.date;

import expressions.ast.FunctionDefinition;
import expressions.evaluator.EvaluationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author martinstraus
 */
public class Date implements FunctionDefinition<LocalDate> {

    @Override
    public LocalDate evaluate(Map<String, Object> context, List<Object> parametersValues) {
        if (parametersValues.size() != 1 || !(parametersValues.get(0) instanceof String)) {
            throw new EvaluationException("'date' expects one parameter of type string.");
        }
        String value = (String) parametersValues.get(0);
        try {
            return LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(value));
        } catch (DateTimeParseException ex) {
            throw new EvaluationException(
                    String.format("Invalid format for date \"%s\". It should be \"yyyy-mm-dd\".", value)
            );
        }
    }

    @Override
    public String name() {
        return "date";
    }

}

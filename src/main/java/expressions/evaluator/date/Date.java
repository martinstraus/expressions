/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator.date;

import expressions.evaluator.EvaluationException;
import expressions.evaluator.SymbolsTable;
import expressions.evaluator.UnaryFunction;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author martinstraus
 */
public class Date extends UnaryFunction<LocalDate> {

    public Date() {
        super("date");
    }

    @Override
    public LocalDate evaluate(SymbolsTable symbolsTable) {
        String value = valueOrException(symbolsTable, String.class);
        try {
            return LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(value));
        } catch (DateTimeParseException ex) {
            throw new EvaluationException(
                    String.format("Invalid format for date \"%s\". It should be \"yyyy-mm-dd\".", value)
            );
        }
    }

}

package expressions.evaluator.date;

import expressions.evaluator.EvaluationException;
import expressions.evaluator.SymbolsTable;
import expressions.evaluator.UnaryFunction;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
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

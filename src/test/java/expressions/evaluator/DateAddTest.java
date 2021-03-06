package expressions.evaluator;

import expressions.Maps;
import expressions.evaluator.date.Add;
import java.time.LocalDate;
import static java.time.Month.*;
import java.time.Period;
import static java.util.Arrays.asList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class DateAddTest {

    public static List<Arguments> values() {
        return asList(
                Arguments.of(LocalDate.of(2019, JANUARY, 1), Period.ofDays(1), LocalDate.of(2019, JANUARY, 2)),
                Arguments.of(LocalDate.of(2019, JANUARY, 1), Period.ofMonths(1), LocalDate.of(2019, FEBRUARY, 1))
        );
    }

    @ParameterizedTest
    @MethodSource("values")
    public void evaluateReturnsExpectedValue(LocalDate date, Period period, LocalDate result) {
        assertEquals(result, new Add().evaluate(symbolsTable(date, period)));
    }
    
    private SymbolsTable symbolsTable(LocalDate date, Period period) {
        return new SymbolsTable(Maps.of("date", date, "period", period));
    }

    @Test
    public void throwsEvaluationExceptionIfInvalidParameters() {
        assertThrows(
                EvaluationException.class,
                () -> new Add().evaluate(new SymbolsTable())
        );
    }
}

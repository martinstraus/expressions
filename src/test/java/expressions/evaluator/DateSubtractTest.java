package expressions.evaluator;

import expressions.Maps;
import expressions.evaluator.date.Subtract;
import java.math.BigDecimal;
import java.time.LocalDate;
import static java.time.Month.*;
import java.time.Period;
import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class DateSubtractTest {

    public static List<Arguments> values() {
        return asList(
                Arguments.of(LocalDate.of(2019, JANUARY, 1), Period.ofDays(1), LocalDate.of(2018, DECEMBER, 31)),
                Arguments.of(LocalDate.of(2019, JANUARY, 1), Period.ofMonths(1), LocalDate.of(2018, DECEMBER, 1))
        );
    }

    public static List<Arguments> invalidParameters() {
        return asList(
                Arguments.of(EMPTY_LIST),
                Arguments.of(asList(BigDecimal.ONE))
        );
    }

    @ParameterizedTest
    @MethodSource("values")
    public void evaluateReturnsExpectedValue(LocalDate date, Period period, LocalDate result) {
        assertEquals(result, new Subtract().evaluate(symbolsTable(date, period)));
    }
    
    
    private SymbolsTable symbolsTable(LocalDate date, Period period) {
        return new SymbolsTable(Maps.of("date", date, "period", period));
    }

    @ParameterizedTest
    @MethodSource("invalidParameters")
    public void throwsEvaluationExceptionIfInvalidParameters(List parameters) {
        assertThrows(EvaluationException.class, () -> new Subtract().evaluate(new SymbolsTable()));
    }
}

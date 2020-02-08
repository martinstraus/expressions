package expressions.evaluator;

import expressions.Maps;
import expressions.evaluator.date.Date;
import java.math.BigDecimal;
import java.time.LocalDate;
import static java.time.Month.*;
import static java.util.Arrays.asList;
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
public class DateTest {
    
    public static List<Arguments> values() {
        return asList(
                Arguments.of("2019-01-01", LocalDate.of(2019, JANUARY, 1)),
                Arguments.of("2019-12-31", LocalDate.of(2019, DECEMBER, 31))
        );
    }
    
    public static List<Arguments> invalidParameters() {
        return asList(
                Arguments.of("dsds"),
                Arguments.of(BigDecimal.ONE)
        );
    }
    
    @ParameterizedTest
    @MethodSource("values")
    public void evaluateReturnsExpectedValue(String value, LocalDate result) {
        assertEquals(
                new Date().evaluate(new SymbolsTable(Maps.of("value", value))),
                result
        );
    }
    
    @ParameterizedTest
    @MethodSource("invalidParameters")
    public void throwsEvaluationExceptionIfInvalidParameters(Object parameter) {
        assertThrows(
                EvaluationException.class,
                () -> new Date().evaluate(new SymbolsTable(Maps.of("value", parameter)))
        );
    }
}

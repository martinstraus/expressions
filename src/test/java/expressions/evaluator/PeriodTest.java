/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator;

import expressions.Maps;
import expressions.evaluator.date.Period;
import expressions.evaluator.date.Units;
import java.math.BigDecimal;
import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import expressions.evaluator.date.Unit;

/**
 *
 * @author martinstraus
 */
public class PeriodTest {

    public static List<Arguments> values() {
        return asList(Arguments.of(new BigDecimal(1), Units.DAYS, java.time.Period.ofDays(1)),
                Arguments.of(new BigDecimal(1), Units.MONTHS, java.time.Period.ofMonths(1)),
                Arguments.of(new BigDecimal(1), Units.YEARS, java.time.Period.ofYears(1))
        );
    }

    public static List<Arguments> invalidParameters() {
        return asList(
                Arguments.of(EMPTY_LIST),
                Arguments.of(BigDecimal.ONE)
        );
    }

    @ParameterizedTest
    @MethodSource("values")
    public void evaluateReturnsExpectedValue(BigDecimal amount, Unit unit, java.time.Period result) {
        assertEquals(
                result,
                new Period().evaluate(new SymbolsTable(Maps.of("amount", amount, "unit", unit)))
        );
    }

    @ParameterizedTest
    @MethodSource("invalidParameters")
    public void throwsEvaluationExceptionIfInvalidParameters(Object parameter) {
        assertThrows(
                EvaluationException.class,
                () -> new Period().evaluate(new SymbolsTable(Maps.of("value", parameter)))
        );
    }
}

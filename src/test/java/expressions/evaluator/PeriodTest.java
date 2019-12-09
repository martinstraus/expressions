/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator;

import expressions.EvaluationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import static java.time.Month.FEBRUARY;
import static java.time.Month.JANUARY;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;
import static java.util.Collections.EMPTY_MAP;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author martinstraus
 */
public class PeriodTest {

    public static List<Arguments> values() {
        return asList(
                Arguments.of(new BigDecimal(1), DateUnits.DAYS, java.time.Period.ofDays(1)),
                Arguments.of(new BigDecimal(1), DateUnits.MONTHS, java.time.Period.ofMonths(1)),
                Arguments.of(new BigDecimal(1), DateUnits.YEARS, java.time.Period.ofYears(1))
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
    public void evaluateReturnsExpectedValue(BigDecimal amount, DateUnit unit, java.time.Period result) {
        assertEquals(
                result,
                new Period().evaluate(EMPTY_MAP, asList(amount, unit))
        );
    }

    @ParameterizedTest
    @MethodSource("invalidParameters")
    public void throwsEvaluationExceptionIfInvalidParameters(List parameters) {
        assertThrows(
                EvaluationException.class,
                () -> new Period().evaluate(EMPTY_MAP, parameters)
        );
    }
}

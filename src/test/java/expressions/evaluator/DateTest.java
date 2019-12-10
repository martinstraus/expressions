/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator;

import expressions.evaluator.date.Date;
import java.math.BigDecimal;
import java.time.LocalDate;
import static java.time.Month.*;
import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;
import static java.util.Collections.EMPTY_MAP;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author martinstraus
 */
public class DateTest {

    public static List<Arguments> values() {
        return asList(
                Arguments.of("2019-01-01", LocalDate.of(2019, JANUARY,1)),
                Arguments.of("2019-12-31", LocalDate.of(2019, DECEMBER,31))
        );
    }

    public static List<Arguments> invalidParameters() {
        return asList(
                Arguments.of(EMPTY_LIST),
                Arguments.of(asList("dsds")),
                Arguments.of(asList(BigDecimal.ONE))
        );
    }

    @ParameterizedTest
    @MethodSource("values")
    public void evaluateReturnsExpectedValue(String value, LocalDate result) {
        assertEquals(
                new Date().evaluate(EMPTY_MAP, asList(value)),
                result
        );
    }

    @ParameterizedTest
    @MethodSource("invalidParameters")
    public void throwsEvaluationExceptionIfInvalidParameters(List parameters) {
        assertThrows(
                EvaluationException.class,
                () -> new Date().evaluate(EMPTY_MAP, parameters)
        );
    }
}

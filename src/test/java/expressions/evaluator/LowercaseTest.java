/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator;

import expressions.EvaluationException;
import java.math.BigDecimal;
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
public class LowercaseTest {

    public static List<Arguments> values() {
        return asList(
                Arguments.of("", ""),
                Arguments.of("A", "a"),
                Arguments.of("aBc", "abc")
        );
    }

    public static List<Arguments> invalidParameters() {
        return asList(
                Arguments.of(EMPTY_LIST),
                Arguments.of(asList("", "")),
                Arguments.of(asList(BigDecimal.ONE))
        );
    }

    @ParameterizedTest
    @MethodSource("values")
    public void evaluateReturnsExpectedValue(String value, String result) {
        assertEquals(
                new Lowercase().evaluate(EMPTY_MAP, asList(value)),
                result
        );
    }

    @ParameterizedTest
    @MethodSource("invalidParameters")
    public void throwsEvaluationExceptionIfInvalidParameters(List parameters) {
        assertThrows(
                EvaluationException.class,
                () -> new Lowercase().evaluate(EMPTY_MAP, parameters)
        );
    }
}

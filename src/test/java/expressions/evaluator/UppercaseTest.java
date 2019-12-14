/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator;

import expressions.Maps;
import expressions.evaluator.string.Uppercase;
import java.math.BigDecimal;
import java.time.LocalDate;
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
 * @author martinstraus
 */
public class UppercaseTest {

    public static List<Arguments> values() {
        return asList(
                Arguments.of("", ""),
                Arguments.of("a", "A"),
                Arguments.of("aBc", "ABC")
        );
    }

    public static List<Arguments> invalidParameters() {
        return asList(
                Arguments.of(EMPTY_LIST),
                Arguments.of(LocalDate.now()),
                Arguments.of(BigDecimal.ONE)
        );
    }

    @ParameterizedTest
    @MethodSource("values")
    public void evaluateReturnsExpectedValue(String value, String result) {
        assertEquals(
                new Uppercase().evaluate(new SymbolsTable(Maps.of("value", value))),
                result
        );
    }

    @ParameterizedTest
    @MethodSource("invalidParameters")
    public void throwsEvaluationExceptionIfInvalidParameters(Object parameter) {
        assertThrows(
                EvaluationException.class,
                () -> new Uppercase().evaluate(new SymbolsTable(Maps.of("value", parameter)))
        );
    }
}

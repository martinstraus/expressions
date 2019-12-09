/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator;

import expressions.ast.Literal;
import expressions.ast.Types;
import java.math.BigDecimal;
import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;
import static java.util.Collections.EMPTY_MAP;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author martinstraus
 */
public class LengthTest {

    public static List<Arguments> values() {
        return asList(
                Arguments.of("", 0),
                Arguments.of("a", 1),
                Arguments.of("abc", 3)
        );
    }

    @ParameterizedTest
    @MethodSource("values")
    public void evaluateReturnsExpectedValue(String value, int expectedLength) {
        assertEquals(
                new Length().evaluate(EMPTY_MAP, asList(value)),
                new BigDecimal(expectedLength)
        );
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import java.math.BigDecimal;
import static java.util.Arrays.asList;
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
public class DifferentToTest {

    public static List<Arguments> testCases() {
        return asList(
                Arguments.of(new BigDecimal(1), new BigDecimal(1), false),
                Arguments.of(new BigDecimal(1), new BigDecimal(2), true)
        );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    public void evaluateReturnsExpectedValue(BigDecimal a, BigDecimal b, boolean result) {
        assertEquals(result, new DifferentTo((context) -> a, (context) -> b).evaluate(EMPTY_MAP));
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import java.math.BigDecimal;
import static java.util.Arrays.asList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author martinstraus
 */
public class NegateTest {

    private static List<Arguments> testCases() {
        return asList(
                Arguments.of(1, -1),
                Arguments.of(-1, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    public void evaluateReturnsCorrectResult(int a, int result) {
        assertEquals(
                new BigDecimal(result),
                new Negate(new Literal(Types.NUMBER, new BigDecimal(a))).evaluate(Collections.EMPTY_MAP)
        );
    }
}

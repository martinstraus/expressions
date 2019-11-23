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
public class DivideTest {

    private static List<Arguments> testCases() {
        return asList(
                Arguments.of(10, 5, 2),
                Arguments.of(100, 1, 100),
                Arguments.of(30, 10, 3)
        );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    public void evaluateReturnsCorrectResult(int a, int b, int result) {
        assertEquals(
                new BigDecimal(result),
                new Divide(
                        (context) -> new BigDecimal(a),
                        (context) -> new BigDecimal(b)
                ).evaluate(Collections.EMPTY_MAP)
        );
    }
}

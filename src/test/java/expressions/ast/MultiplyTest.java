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
public class MultiplyTest {

    private static List<Arguments> testCases() {
        return asList(
                Arguments.of(10, 5, 50),
                Arguments.of(0, 1, 0),
                Arguments.of(30, 3, 90)
        );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    public void evaluateReturnsCorrectResult(int a, int b, int result) {
        assertEquals(
                new BigDecimal(result),
                new Multiply(
                        (context) -> new BigDecimal(a),
                        (context) -> new BigDecimal(b)
                ).evaluate(Collections.EMPTY_MAP)
        );
    }
}

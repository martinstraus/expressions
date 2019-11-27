/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions;

import java.math.BigDecimal;
import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_MAP;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author martinstraus
 */
public class SimpleEvaluatorTest {

    private static List<Arguments> testExpressions() {
        return asList(
                Arguments.of(
                        "a+b",
                        map("a", new BigDecimal(1), "b", new BigDecimal(2)),
                        map("result", new BigDecimal(3))
                ),
                Arguments.of(
                        "a-b",
                        map("a", new BigDecimal(3), "b", new BigDecimal(2)),
                        map("result", new BigDecimal(1))
                ),
                Arguments.of(
                        "a^b",
                        map("a", new BigDecimal(2), "b", new BigDecimal(3)),
                        map("result", new BigDecimal(8))
                ),
                Arguments.of(
                        "a > b",
                        map("a", new BigDecimal(10), "b", new BigDecimal(1)),
                        map("result", true)
                ),
                Arguments.of(
                        "a ^ b",
                        map("a", new BigDecimal(2), "b", new BigDecimal(3)),
                        map("result", new BigDecimal(8))
                ),
                Arguments.of(
                        "a",
                        map("a", new BigDecimal(4)),
                        map("result", new BigDecimal(4))
                ),
                Arguments.of(
                        "-a",
                        map("a", new BigDecimal(4)),
                        map("result", new BigDecimal(-4))
                ),
                Arguments.of(
                        "(-a) + b",
                        map("a", new BigDecimal(3), "b", new BigDecimal(5)),
                        map("result", new BigDecimal(2))
                ),
                Arguments.of(
                        "4",
                        EMPTY_MAP,
                        map("result", new BigDecimal(4))
                ),
                Arguments.of(
                        "-4",
                        EMPTY_MAP,
                        map("result", new BigDecimal(-4))
                ),
                Arguments.of(
                        "a / b - 4",
                        map("a", new BigDecimal(100), "b", new BigDecimal(50)),
                        map("result", new BigDecimal(-2))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("testExpressions")
    public void evaluateReturnsExpectedResult(String expression, Map<String, Object> context, Map<String, Object> expectedResult) {
        assertEquals(
                expectedResult,
                new SimpleEvaluator().evaluate(expression, context)
                
        );
    }

    private static Map<String, Object> map(String key1, Object value1) {
        Map<String, Object> map = new HashMap<>();
        map.put(key1, value1);
        return map;
    }

    private static Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> map = new HashMap<>();
        map.put(key1, value1);
        map.put(key2, value2);
        return map;
    }

}

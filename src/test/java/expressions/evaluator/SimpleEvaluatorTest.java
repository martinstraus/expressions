/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator;

import java.math.BigDecimal;
import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_MAP;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
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
            ),
            Arguments.of(
                "a in {1,2,3}",
                map("a", new BigDecimal(1)),
                map("result", true)
            ),
            Arguments.of(
                "a in {1,2,3}",
                map("a", new BigDecimal(4)),
                map("result", false)
            ),
            Arguments.of(
                "a in {}",
                map("a", new BigDecimal(4)),
                map("result", false)
            ),
            Arguments.of(
                "not (a > b)",
                map("a", new BigDecimal(4), "b", new BigDecimal(8)),
                map("result", true)
            ),
            Arguments.of(
                "a and b",
                map("a", true, "b", true),
                map("result", true)
            ),
            Arguments.of(
                "a and b",
                map("a", false, "b", true),
                map("result", false)
            ),
            Arguments.of(
                "a or b",
                map("a", false, "b", false),
                map("result", false)
            ),
            Arguments.of(
                "a or b",
                map("a", false, "b", true),
                map("result", true)
            ),
            Arguments.of(
                "a or b",
                map("a", true, "b", false),
                map("result", true)
            ),
            Arguments.of(
                "a or b",
                map("a", true, "b", true),
                map("result", true)
            ),
            Arguments.of(
                "(a > 1) and (b > 2)",
                map("a", new BigDecimal(2), "b", new BigDecimal(3)),
                map("result", true)
            ),
            Arguments.of(
                "a = \"abc\"",
                map("a", "abc"),
                map("result", true)
            ),
            Arguments.of(
                "a in {\"abc\", \"def\"}",
                map("a", "abc"),
                map("result", true)
            ),
            Arguments.of(
                "a in {\"abc\", \"def\"}",
                map("a", "x"),
                map("result", false)
            ),
            Arguments.of(
                "def f(x) <- x+1; f(a)",
                map("a", new BigDecimal(2)),
                map("result", new BigDecimal(3))
            ),
            Arguments.of(
                "matches(x, \"[abc]\")",
                map("x", "a"),
                map("result", true)
            ),
            Arguments.of(
                "length(x)",
                map("x", "abc"),
                map("result", new BigDecimal(3))
            ),
            Arguments.of(
                "split(text, regexp)",
                map("text", "a b c", "regexp", " "),
                map("result", asList("a", "b", "c"))
            ),
            Arguments.of(
                "tag in split(tags, \",\")",
                map("tags", "tag1,tag2,tag3", "tag", "tag2"),
                map("result", true)
            ),
            Arguments.of(
                "lowercase(a) = \"abc\"",
                map("a", "aBc"),
                map("result", true)
            ),
            Arguments.of(
                "uppercase(a) = \"ABC\"",
                map("a", "aBc"),
                map("result", true)
            ),
            Arguments.of(
                "date(a) = date(\"2019-01-01\")",
                map("a", "2019-01-01"),
                map("result", true)
            ),
            Arguments.of(
                "add(date(\"2019-01-01\"), period(1, days)) = date(\"2019-01-02\")",
                EMPTY_MAP,
                map("result", true)
            ),
            Arguments.of(
                "subtract(date(\"2019-01-01\"), period(1, months)) = date(\"2018-12-01\")",
                EMPTY_MAP,
                map("result", true)
            ),
            Arguments.of(
                "def f(x)<-x+1; def g(x)<-f(x)+2; g(a)",
                map("a", 1),
                map("result", new BigDecimal(4))
            ),
            Arguments.of(
                "[\"a\"<-a]",
                map("a", 1),
                map("result", map("a", new BigDecimal(1)))
            ),
            Arguments.of(
                "x[\"a\"]",
                map("x", map("a", new BigDecimal(1))),
                map("result", new BigDecimal(1))
            ),
            Arguments.of(
                "x[\"a\"][\"b\"]",
                map("x", map("a", map("b", new BigDecimal(1)))),
                map("result", new BigDecimal(1))
            ),
            Arguments.of(
                "def a<-2; a",
                EMPTY_MAP,
                map("result", new BigDecimal(2))
            ),
            Arguments.of(
                "def f(x) <- { def y <- x+2; y} f(z)",
                map("z", new BigDecimal(1)),
                map("result", new BigDecimal(3))
            )
        );
    }

    private static List<Arguments> arrayCases() {
        return asList(
            Arguments.of(
                "[a,2,3]",
                map("a", new BigDecimal(1)),
                map("result", asList(new BigDecimal(1), new BigDecimal(2), new BigDecimal(3)))
            )
        );
    }

    private static List<Arguments> evaluationExceptionExpressions() {
        return asList(
            Arguments.of("a>b", map("a", new BigDecimal(1), "b", "b")),
            Arguments.of("a<b", map("a", new BigDecimal(1), "b", "b"))
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

    @ParameterizedTest
    @MethodSource({"testExpressions", "arrayCases"})
    public void evaluateReturnsExpectedResult(String expression, Map<String, Object> context, Map<String, Object> expectedResult) {
        System.out.printf(
            "Expression \"%s\" for context %s should have result %s.\n",
            expression,
            context.toString(),
            expectedResult.toString()
        );
        assertEquals(
            expectedResult,
            new SimpleEvaluator().evaluate(expression, context)
        );
    }

    @ParameterizedTest
    @MethodSource("evaluationExceptionExpressions")
    public void throwsEvaluationExceptionIfExpressionIsInvalid(String expression, Map<String, Object> context) {
        assertThrows(
            EvaluationException.class,
            () -> new SimpleEvaluator().evaluate(expression, context)
        );
    }

    @Test
    public void convertsIntegersCorrectly() {
        assertEquals(
            map("result", new BigDecimal(3)),
            new SimpleEvaluator().evaluate("a+b", map("a", 1, "b", 2))
        );
    }

    @Test
    public void throwsEvaluationExceptionIfFunctionNotDefined() {
        assertThrows(
            EvaluationException.class,
            () -> new SimpleEvaluator().evaluate("f(a)", EMPTY_MAP)
        );
    }
}

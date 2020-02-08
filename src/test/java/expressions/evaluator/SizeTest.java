package expressions.evaluator;

import expressions.Maps;
import expressions.evaluator.string.Length;
import java.math.BigDecimal;
import static java.util.Arrays.asList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class SizeTest {

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
                new Length().evaluate(new SymbolsTable(Maps.of("value", value))),
                new BigDecimal(expectedLength)
        );
    }
}

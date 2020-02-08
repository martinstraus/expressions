package expressions.evaluator;

import expressions.Maps;
import expressions.evaluator.string.Split;
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
public class SplitTest {

    public static List<Arguments> values() {
        return asList(
                Arguments.of("a b c", " ", asList("a", "b", "c"))
        );
    }

    @ParameterizedTest
    @MethodSource("values")
    public void evaluateReturnsExpectedValue(String value, String regexp, List<String> expectedParts) {
        assertEquals(
                new Split().evaluate(new SymbolsTable(Maps.of("value", value, "regexp", regexp))),
                expectedParts
        );

    }
}

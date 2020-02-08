package expressions.ast;

import expressions.evaluator.SymbolsTable;
import java.math.BigDecimal;
import static java.util.Arrays.asList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
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
                new Negate(new Literal(Types.NUMBER, new BigDecimal(a))).evaluate(new SymbolsTable())
        );
    }
}

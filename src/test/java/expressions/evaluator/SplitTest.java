/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author martinstraus
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
                new Split().evaluate(new SymbolsTable(Maps.of("a", value, "b", regexp))),
                expectedParts
        );

    }
}

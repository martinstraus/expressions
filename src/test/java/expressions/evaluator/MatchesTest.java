/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator;

import expressions.EvaluationException;
import expressions.ast.Expression;
import expressions.ast.Literal;
import expressions.ast.Types;
import static java.util.Arrays.asList;
import static java.util.Collections.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author martinstraus
 */
public class MatchesTest {

    public static List<Arguments> wrongParametersCount() {
        return asList(
                Arguments.of(EMPTY_LIST),
                Arguments.of(asList(new Literal<String>(Types.STRING, "a"))),
                Arguments.of(asList(
                        new Literal<String>(Types.STRING, "a"),
                        new Literal<String>(Types.STRING, "b"),
                        new Literal<String>(Types.STRING, "c")
                ))
        );
    }

    @ParameterizedTest
    @MethodSource("wrongParametersCount")
    public void throwsEvaluationExceptionIfItDoesntReceiveTwoParameters(List<Expression> parameters) {
        assertThrows(EvaluationException.class, () -> new Matches().evaluate(EMPTY_MAP, EMPTY_LIST));
    }
}

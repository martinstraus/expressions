/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator;

import expressions.evaluator.string.Matches;
import expressions.ast.Expression;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 *
 * @author martinstraus
 */
public class MatchesTest {

    
    @Test
    public void throwsEvaluationExceptionIfItDoesntReceiveTwoParameters() {
        assertThrows(
                EvaluationException.class, 
                () -> new Matches().evaluate(new SymbolsTable())
        );
    }
}

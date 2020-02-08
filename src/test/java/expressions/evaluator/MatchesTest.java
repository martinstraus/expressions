package expressions.evaluator;

import expressions.evaluator.string.Matches;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
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

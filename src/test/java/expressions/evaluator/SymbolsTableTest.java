package expressions.evaluator;

import expressions.ast.Types;
import expressions.ast.Variable;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author martinstraus
 */
public class SymbolsTableTest {
    @Test
    public void eachSymbolTableHasItsOwnResolution(){
        SymbolsTable parent = new SymbolsTable();
        parent.put(new Random());
        SymbolsTable child = parent.push();
        child.put(new Variable(Types.STRING, "a"));
        assertTrue(parent.function("random").isPresent());
        assertTrue(child.function("random").isPresent());
        assertTrue(parent.value("a").isEmpty());
        assertTrue(child.value("a").isPresent());
    }
}
package expressions.ast;

import expressions.evaluator.SymbolsTable;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class DefineValueTest {
    @Test
    public void symbolsTableContainsValueAfterExecution() {
        SymbolsTable symbolsTable = mock(SymbolsTable.class);
        new DefineValue("a", new Literal<>(Types.NUMBER, new BigDecimal(1))).execute(symbolsTable);
        verify(symbolsTable).put("a", new BigDecimal(1));
    }
}

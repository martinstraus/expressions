package expressions.ast;

import expressions.TestObject;
import expressions.evaluator.SymbolsTable;
import java.math.BigDecimal;
import static java.math.BigDecimal.*;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author martinstraus
 */
public class PropertyReferenceTest {

    @Test
    public void singlePropertyReferenceResolvesCorrectly() {
        Expression<TestObject> valueExpression = mock(Expression.class);
        TestObject testObject = new TestObject(ONE, null);
        when(valueExpression.evaluate(any(SymbolsTable.class))).thenReturn(testObject);
        SymbolsTable symbolsTable = mock(SymbolsTable.class);
        assertEquals(ONE, new PropertyReference<BigDecimal>(valueExpression, asList("value")).evaluate(symbolsTable));
    }
    
    @Test
    public void nestedPropertyReferenceResolvesCorrectly() {
        Expression<TestObject> valueExpression = mock(Expression.class);
        TestObject testObject = new TestObject(ONE, new TestObject(new BigDecimal(2)));
        when(valueExpression.evaluate(any(SymbolsTable.class))).thenReturn(testObject);
        SymbolsTable symbolsTable = mock(SymbolsTable.class);
        assertEquals(new BigDecimal(2), new PropertyReference<BigDecimal>(valueExpression, asList("nested", "value")).evaluate(symbolsTable));
    }
}

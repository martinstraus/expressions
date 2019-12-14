/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.parser;

import expressions.Maps;
import expressions.ast.Expression;
import expressions.ast.FunctionCall;
import expressions.ast.FunctionDefinition;
import expressions.ast.Literal;
import expressions.ast.Types;
import expressions.evaluator.SymbolsTable;
import java.math.BigDecimal;
import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_MAP;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author martinstraus
 */
public class FunctionCallTest {

    @Test
    public void callsFunctionAndReturnsValue() {
        BigDecimal value = new BigDecimal(new Random().nextDouble());
        FunctionDefinition function = mock(FunctionDefinition.class);
        doReturn(value).when(function).evaluate(any(SymbolsTable.class));
        SymbolsTable symbolsTable = new SymbolsTable(Optional.empty(), Maps.of("f", function), EMPTY_MAP);
        List<Expression> parameters = asList(new Literal<BigDecimal>(Types.NUMBER, new BigDecimal(1)));
        BigDecimal result = new FunctionCall<BigDecimal>("f", parameters).evaluate(symbolsTable);
        assertEquals(value, result);
    }
}

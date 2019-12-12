/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.parser;

import expressions.ast.FunctionCall;
import expressions.ast.FunctionDefinition;
import expressions.ast.Literal;
import expressions.ast.Types;
import java.math.BigDecimal;
import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_MAP;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        doReturn(value).when(function).evaluate(any(Map.class), any(List.class));
        BigDecimal result = new FunctionCall<BigDecimal>(
                "f",
                asList(new Literal<BigDecimal>(Types.NUMBER, new BigDecimal(1))),
                functions("f", new FunctionDefinition() {
                    @Override
                    public String name() {
                        return "f";
                    }

                    @Override
                    public Object evaluate(Map context, List parametersValues) {
                        return value;
                    }
                })
        ).evaluate(EMPTY_MAP);
        assertEquals(value, result);
    }

    private Map<String, FunctionDefinition> functions(String name, FunctionDefinition function) {
        Map<String, FunctionDefinition> functions = new HashMap<>();
        functions.put("f", function);
        return functions;
    }
}

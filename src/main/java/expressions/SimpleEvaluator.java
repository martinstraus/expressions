/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions;

import expressions.parser.AntlrParser;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author martinstraus
 */
public class SimpleEvaluator implements Evaluator {
    
    @Override
    public Map<String, Object> evaluate(String expression, Map<String, Object> context) {
        return mapWith(
                "result",
                new AntlrParser().parse(expression).evaluate(withNumbersTransformedToBigDecimal(context))
        );
    }
    
    private Map<String, Object> withNumbersTransformedToBigDecimal(Map<String, Object> context) {
        return context.entrySet().stream().collect(Collectors.toMap(
                (e) -> e.getKey(),
                (e) -> valueAsBigDecimalIfInteger(e.getValue())
        ));
    }
    
    private Object valueAsBigDecimalIfInteger(Object value) {
        return value instanceof Integer ? new BigDecimal((Integer) value) : value;
    }
    
    private Map<String, Object> mapWith(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
    
}

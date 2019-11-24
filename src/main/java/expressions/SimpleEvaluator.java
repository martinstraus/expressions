/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions;

import expressions.parser.AntlrParser;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author martinstraus
 */
public class SimpleEvaluator implements Evaluator {

    @Override
    public Map<String, Object> evaluate(String expression, Map<String, Object> context) {
        return mapWith("result", new AntlrParser().parse(expression).evaluate(context));
    }

    private Map<String, Object> mapWith(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

}

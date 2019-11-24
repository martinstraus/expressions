/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions;

import java.util.Map;

/**
 *
 * @author martinstraus
 */
public class TestCase {

    private final String expression;
    private final Map<String, Object> context;
    private final Map<String, Object> expectedResult;

    public TestCase(String expression, Map<String, Object> context, Map<String, Object> expectedResult) {
        this.expression = expression;
        this.context = context;
        this.expectedResult = expectedResult;
    }

    public String expression() {
        return expression;
    }

    public Map<String, Object> context() {
        return context;
    }

    public Map<String, Object> expectedResult() {
        return expectedResult;
    }

}

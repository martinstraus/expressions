/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import java.util.Map;

/**
 *
 * @author martinstraus
 */
public class File {

    private final Map<String, SimpleFunctionDefinition> functions;
    private final Expression expression;

    public File(Map<String, SimpleFunctionDefinition> functions, Expression expression) {
        this.functions = functions;
        this.expression = expression;
    }

    public Object evaluate(Map<String, Object> context) {
        return expression.evaluate(context);
    }

}

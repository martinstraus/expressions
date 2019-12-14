/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import expressions.evaluator.SymbolsTable;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author martinstraus
 */
public class File {

    private final List<FunctionDefinition> functions;
    private final Expression expression;

    public File(List<FunctionDefinition> functions, Expression expression) {
        this.functions = functions;
        this.expression = expression;
    }
    
    public List<FunctionDefinition> functions() {
        return Collections.unmodifiableList(functions);
    }

    public Object evaluate(SymbolsTable SymbolsTable) {
        return expression.evaluate(SymbolsTable);
    }

}

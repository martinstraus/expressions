/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import expressions.evaluator.SymbolsTable;
import java.util.List;

/**
 *
 * @author martinstraus
 */
public interface FunctionDefinition<T> {
    
    String name();
    
    List<ParameterDefinition> parameters();

    T evaluate(SymbolsTable symbolsTable);
    
}

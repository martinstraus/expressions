/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import expressions.evaluator.SymbolsTable;

/**
 *
 * @author martinstraus
 */
public interface Expression<T> {
    
    Type type();
    
    T evaluate(SymbolsTable symbolsTable);
}

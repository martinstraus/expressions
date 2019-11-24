/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.parser;

import expressions.ast.Expression;

/**
 *
 * @author martinstraus
 */
public interface Parser {
    
    Expression parse(String expression);
}

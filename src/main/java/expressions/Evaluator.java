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
public interface Evaluator {
    
    Map<String, Object> evaluate(String expression, Map<String, Object> context);
}

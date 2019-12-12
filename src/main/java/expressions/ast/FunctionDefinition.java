/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import java.util.List;
import java.util.Map;

/**
 *
 * @author martinstraus
 */
public interface FunctionDefinition<T> {
    
    String name();

    T evaluate(Map<String, Object> context, List<Object> parametersValues);
    
}

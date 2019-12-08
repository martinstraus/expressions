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
@FunctionalInterface
public interface FunctionDefinition<T> {

    T evaluate(Map<String, Object> context, List<Object> parametersValues);
    
}

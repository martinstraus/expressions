/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator;

import java.nio.file.Path;
import java.util.Map;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public interface Evaluator {

    Map<String, Object> evaluate(String expression, Map<String, Object> context);

    Map<String, Object> evaluate(Path path, Map<String, Object> context);
}

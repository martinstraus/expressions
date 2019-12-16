/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.cli;

import expressions.evaluator.SimpleEvaluator;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.util.Collections.EMPTY_MAP;
import java.util.Map;

/**
 *
 * @author martinstraus
 */
public class CLI {
    
    public static void main(String[] args) {
        for (String file : args) {
            evaluate(Paths.get(file));
        }
    }
    
    private static void evaluate(Path file) {
        Map<String, Object> result = new SimpleEvaluator().evaluate(file, EMPTY_MAP);
        result.forEach((k, v) -> System.out.printf("%s=%s\n", k, v != null ? v.toString() : "(null)"));
    }
}

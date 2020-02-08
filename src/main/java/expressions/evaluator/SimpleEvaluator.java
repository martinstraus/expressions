/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator;

import expressions.ast.Program;
import expressions.evaluator.array.Size;
import expressions.evaluator.collections.In;
import expressions.evaluator.date.Add;
import expressions.evaluator.date.Date;
import expressions.evaluator.date.Period;
import expressions.evaluator.date.Subtract;
import expressions.evaluator.string.Length;
import expressions.evaluator.string.Lowercase;
import expressions.evaluator.string.Matches;
import expressions.evaluator.string.Split;
import expressions.evaluator.string.Uppercase;
import expressions.parser.antlr.AntlrParser;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class SimpleEvaluator implements Evaluator {

    private final SymbolsTable symbolsTableTemplate;

    public SimpleEvaluator() {
        symbolsTableTemplate = new SymbolsTable();
        symbolsTableTemplate.put(new Matches());
        symbolsTableTemplate.put(new Length());
        symbolsTableTemplate.put(new Split());
        symbolsTableTemplate.put(new Lowercase());
        symbolsTableTemplate.put(new Uppercase());
        symbolsTableTemplate.put(new Date());
        symbolsTableTemplate.put(new Period());
        symbolsTableTemplate.put(new Add());
        symbolsTableTemplate.put(new Subtract());
        symbolsTableTemplate.put(new Size());
        symbolsTableTemplate.put(new In());
    }

    @Override
    public Map<String, Object> evaluate(String expression, Map<String, Object> context) {
        return evaluate(new AntlrParser().parseProgram(expression), context);
    }

    @Override
    public Map<String, Object> evaluate(Path path, Map<String, Object> context) {
        return evaluate(new AntlrParser().parseProgram(path), context);
    }

    public Map<String, Object> evaluate(Program file, Map<String, Object> context) {
        SymbolsTable symbolsTable = symbolsTableTemplate.push();
        symbolsTable.putValues(withNumbersTransformedToBigDecimal(context));
        symbolsTable.add(file.functions());
        return mapWith("result", file.evaluate(symbolsTable));
    }

    private Map<String, Object> withNumbersTransformedToBigDecimal(Map<String, Object> context) {
        return context.entrySet().stream().collect(Collectors.toMap(
            (e) -> e.getKey(),
            (e) -> valueAsBigDecimalIfInteger(e.getValue())
        ));
    }

    private Object valueAsBigDecimalIfInteger(Object value) {
        return value instanceof Integer ? new BigDecimal((Integer) value) : value;
    }

    private Map<String, Object> mapWith(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

}

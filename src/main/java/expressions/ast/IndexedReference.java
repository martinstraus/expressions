package expressions.ast;

import expressions.evaluator.EvaluationException;
import expressions.evaluator.SymbolsTable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class IndexedReference<T> implements Expression<T> {

    private final Expression map;
    private final Expression key;

    public IndexedReference(Expression map, Expression key) {
        this.map = map;
        this.key = key;
    }

    @Override
    public Type type() {
        return Types.UNKNOWN;
    }

    @Override
    public T evaluate(SymbolsTable symbolsTable) {
        Object value = map.evaluate(symbolsTable);
        if (value instanceof java.util.List) {
            return (T) ((List) value).get(((BigDecimal) key.evaluate(symbolsTable)).intValue());
        } else if (value instanceof java.util.Map) {
            return (T) ((java.util.Map) value).get(key.evaluate(symbolsTable));
        } else {
            throw new EvaluationException("Expression should be an array or a map.");
        }
    }

}

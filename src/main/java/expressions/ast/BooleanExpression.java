package expressions.ast;

import expressions.evaluator.SymbolsTable;
import java.util.Map;
import java.util.function.BiFunction;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class BooleanExpression<T> implements Expression<Boolean> {

    private final BiFunction<T, T, Boolean> comparator;
    private final Expression<T> left;
    private final Expression<T> right;

    public BooleanExpression(BiFunction<T, T, Boolean> comparator, Expression<T> left, Expression<T> right) {
        this.comparator = comparator;
        this.left = left;
        this.right = right;
    }

    @Override
    public Boolean evaluate(SymbolsTable symbolsTable) {
        return comparator.apply(left.evaluate(symbolsTable), right.evaluate(symbolsTable));
    }

    @Override
    public Type type() {
        return Types.BOOLEAN;
    }

}

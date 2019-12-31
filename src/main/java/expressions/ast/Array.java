package expressions.ast;

import expressions.evaluator.SymbolsTable;
import java.util.Collections;
import java.util.List;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author martinstraus
 */
public class Array implements Expression<java.util.List> {

    private final List<Expression> values;

    public Array(List<Expression> values) {
        this.values = Collections.unmodifiableList(values);
    }

    @Override
    public Type type() {
        return Types.ARRAY;
    }

    @Override
    public List evaluate(SymbolsTable symbolsTable) {
        return values.stream().map((value) -> value.evaluate(symbolsTable)).collect(toList());
    }

}

package expressions.ast;

import expressions.evaluator.SymbolsTable;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toMap;

public class Map implements Expression<java.util.Map> {

    public static final class Entry {
        final Expression key;
        final Expression value;

        public Entry(Expression key, Expression value) {
            this.key = key;
            this.value = value;
        }
    }

    private final List<Entry> entries;

    public Map(List<Entry> entries) {
        this.entries = Collections.unmodifiableList(entries);
    }

    @Override
    public Type type() {
        return Types.MAP;
    }

    @Override
    public java.util.Map evaluate(SymbolsTable symbolsTable) {
        return entries.stream().collect(toMap(
                (e)-> e.key.evaluate(symbolsTable),
                (e)-> e.value.evaluate(symbolsTable)
        ));
    }
}

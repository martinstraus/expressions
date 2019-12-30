package expressions.evaluator;

import expressions.ast.FunctionDefinition;
import expressions.ast.Variable;
import static java.util.Collections.EMPTY_MAP;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A symbols table holds information for the current context.
 *
 * @author martinstraus
 */
public class SymbolsTable {

    private Map<String, Object> values;
    private Map<String, FunctionDefinition> functions;
    private Optional<SymbolsTable> parent;

    public SymbolsTable() {
        this(Optional.empty(), EMPTY_MAP, EMPTY_MAP);
    }

    public SymbolsTable(Map<String, Object> values) {
        this(Optional.empty(), EMPTY_MAP, values);
    }

    public SymbolsTable(Optional<SymbolsTable> parent, Map<String, FunctionDefinition> functions, Map<String, Object> values) {
        this.parent = parent;
        this.functions = new HashMap<>(functions);
        this.values = new HashMap<>(values);
    }

    public Optional<FunctionDefinition> function(String name) {
        FunctionDefinition function = functions.get(name);
        return (function == null && parent.isPresent())
                ? parent.get().function(name)
                : Optional.ofNullable(function);
    }

    public Optional<Object> value(String name) {
        return Optional.ofNullable(values.get(name));
    }

    public void put(FunctionDefinition function) {
        functions.put(function.name(), function);
    }

    public void put(Variable value) {
        values.put(value.name(), value);
    }

    public void put(String name, Object value) {
        values.put(name, value);
    }

    public void putValues(Map<String, Object> values) {
        this.values.putAll(values);
    }

    public void add(List<FunctionDefinition> functions) {
        functions.forEach((f) -> this.functions.put(f.name(), f));
    }

    public SymbolsTable push() {
        return new SymbolsTable(parent, functions, EMPTY_MAP);
    }

    public Optional<SymbolsTable> pop() {
        return parent;
    }
}

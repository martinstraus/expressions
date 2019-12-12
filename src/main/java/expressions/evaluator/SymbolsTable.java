package expressions.evaluator;

import expressions.ast.FunctionDefinition;
import expressions.ast.Variable;
import static java.util.Collections.EMPTY_MAP;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A symbols table holds information for the current context.
 *
 * @author martinstraus
 */
public class SymbolsTable {

    private Map<String, Variable> values;
    private Map<String, FunctionDefinition> functions;
    private Optional<SymbolsTable> parent;

    public SymbolsTable() {
        this(Optional.empty(), EMPTY_MAP, EMPTY_MAP);
    }

    public SymbolsTable(SymbolsTable parent) {
        this(Optional.of(parent), EMPTY_MAP, EMPTY_MAP);
    }

    public SymbolsTable(Optional<SymbolsTable> parent, Map<String, FunctionDefinition> functions, Map<String, Variable> values) {
        this.parent = parent;
        this.values = new HashMap<>(values);
        this.functions = new HashMap<>(functions);
    }

    public Optional<FunctionDefinition> function(String name) {
        FunctionDefinition function = functions.get(name);
        return (function == null && parent.isPresent())
                ? parent.get().function(name)
                : Optional.ofNullable(function);
    }

    public Optional<Variable> value(String name) {
        Variable value = values.get(name);
        return (value == null && parent.isPresent())
                ? parent.get().value(name)
                : Optional.ofNullable(value);
    }

    public void put(FunctionDefinition function) {
        functions.put(function.name(), function);
    }

    public void put(Variable value) {
        values.put(value.name(), value);
    }

    public SymbolsTable push() {
        return new SymbolsTable(parent, functions, values);
    }

    public Optional<SymbolsTable> pop() {
        return parent;
    }
}

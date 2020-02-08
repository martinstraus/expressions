package expressions.ast;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class ParameterDefinition {

    private final String name;

    public ParameterDefinition(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

}

package expressions.ast;

import java.util.function.BiFunction;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class Equals implements BiFunction<Object, Object, Boolean> {

    @Override
    public Boolean apply(Object t, Object u) {
        return t.equals(u);
    }
    
}

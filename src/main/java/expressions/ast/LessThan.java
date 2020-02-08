package expressions.ast;

import java.math.BigDecimal;
import java.util.function.BiFunction;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class LessThan implements BiFunction<BigDecimal, BigDecimal, Boolean> {

    @Override
    public Boolean apply(BigDecimal t, BigDecimal u) {
        return t.compareTo(u) < 0;
    }

}

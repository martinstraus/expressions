package expressions.evaluator.date;

import java.math.BigDecimal;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public interface Unit {
    
    java.time.Period period(BigDecimal amount);
}

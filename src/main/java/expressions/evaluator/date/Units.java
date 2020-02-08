package expressions.evaluator.date;

import java.math.BigDecimal;
import java.time.Period;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class Units {

    public static final Unit DAYS = new Unit() {
        @Override
        public Period period(BigDecimal amount) {
            return Period.ofDays(amount.intValue());
        }
    };
    public static final Unit MONTHS = new Unit() {
        @Override
        public Period period(BigDecimal amount) {
            return Period.ofMonths(amount.intValue());
        }
    };
    public static final Unit YEARS = new Unit() {
        @Override
        public Period period(BigDecimal amount) {
            return Period.ofYears(amount.intValue());
        }
    };
}

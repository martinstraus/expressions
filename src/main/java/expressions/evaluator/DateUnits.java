/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator;

import java.math.BigDecimal;
import java.time.Period;

/**
 *
 * @author martinstraus
 */
public class DateUnits {

    public static final DateUnit DAYS = new DateUnit() {
        @Override
        public Period period(BigDecimal amount) {
            return Period.ofDays(amount.intValue());
        }
    };
    public static final DateUnit MONTHS = new DateUnit() {
        @Override
        public Period period(BigDecimal amount) {
            return Period.ofMonths(amount.intValue());
        }
    };
    public static final DateUnit YEARS = new DateUnit() {
        @Override
        public Period period(BigDecimal amount) {
            return Period.ofYears(amount.intValue());
        }
    };
}

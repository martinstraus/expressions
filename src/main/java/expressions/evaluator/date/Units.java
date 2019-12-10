/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator.date;

import java.math.BigDecimal;
import java.time.Period;

/**
 *
 * @author martinstraus
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

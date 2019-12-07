/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import java.math.BigDecimal;
import java.util.function.BiFunction;

/**
 *
 * @author martinstraus
 */
public class LessThan implements BiFunction<BigDecimal, BigDecimal, Boolean> {

    @Override
    public Boolean apply(BigDecimal t, BigDecimal u) {
        return t.compareTo(u) < 0;
    }

}

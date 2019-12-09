/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator;

import java.math.BigDecimal;

/**
 *
 * @author martinstraus
 */
public interface DateUnit {
    
    java.time.Period period(BigDecimal amount);
}

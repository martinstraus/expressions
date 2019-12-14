/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator.numbers;

import expressions.ast.FunctionDefinition;
import expressions.ast.ParameterDefinition;
import expressions.evaluator.SymbolsTable;
import expressions.evaluator.SymbolsTable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author martinstraus
 */
public class Random implements FunctionDefinition<BigDecimal> {

    private final java.util.Random random = new java.util.Random();

    @Override
    public BigDecimal evaluate(SymbolsTable symbolsTable) {
        return new BigDecimal(random.nextDouble());
    }

    @Override
    public String name() {
        return "random";
    }

    @Override
    public List<ParameterDefinition> parameters() {
        return Collections.EMPTY_LIST;
    }

}

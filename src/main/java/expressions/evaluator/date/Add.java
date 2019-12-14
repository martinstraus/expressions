/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator.date;

import expressions.evaluator.BinaryFunction;
import expressions.evaluator.EvaluationException;
import expressions.evaluator.SymbolsTable;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 *
 * @author martinstraus
 */
public class Add extends BinaryFunction<LocalDate> {

    public Add() {
        super("add");
    }

    @Override
    public LocalDate evaluate(SymbolsTable symbolsTable) {
        LocalDate date = a(symbolsTable, LocalDate.class);
        Period period = b(symbolsTable, Period.class);
        return date.plus(period);
    }
}

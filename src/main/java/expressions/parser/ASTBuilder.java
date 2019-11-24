/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.parser;

import expressions.ast.Add;
import expressions.ast.BooleanExpression;
import expressions.ast.Exponentiation;
import expressions.ast.Expression;
import expressions.ast.Subtract;
import expressions.ast.Variable;
import expressions.parser.antlr4.FunctionBaseListener;
import expressions.parser.antlr4.FunctionParser;
import java.math.BigDecimal;
import java.util.Stack;
import java.util.function.BiFunction;

/**
 *
 * @author martinstraus
 */
public class ASTBuilder extends FunctionBaseListener {

    private static final BiFunction<BigDecimal, BigDecimal, Boolean> BIG_DECIMAL_EQUALS = (a, b) -> a.compareTo(b) == 0;
    private static final BiFunction<BigDecimal, BigDecimal, Boolean> BIG_DECIMAL_GREATER_TAHN = (a, b) -> a.compareTo(b) > 0;
    private static final BiFunction<BigDecimal, BigDecimal, Boolean> BIG_DECIMAL_LESS_TAHN = (a, b) -> a.compareTo(b) < 0;

    private final Stack<Expression> stack;

    public ASTBuilder() {
        this.stack = new Stack<>();
    }

    @Override
    public void exitRelop(FunctionParser.RelopContext ctx) {

    }

    @Override
    public void exitPower(FunctionParser.PowerContext ctx) {
        Expression<BigDecimal> exponent = stack.pop();
        Expression<BigDecimal> base = stack.pop();
        stack.push(new Exponentiation(base, exponent));
    }

    @Override
    public void exitBooleanExpression(FunctionParser.BooleanExpressionContext ctx) {
        Expression right = stack.pop();
        Expression left = stack.pop();
        if (ctx.EQ() != null) {
            stack.add(new BooleanExpression(BIG_DECIMAL_EQUALS, left, right));
        } else if (ctx.GT() != null) {
            stack.add(new BooleanExpression(BIG_DECIMAL_GREATER_TAHN, left, right));
        } else if (ctx.LT() != null) {
            stack.add(new BooleanExpression(BIG_DECIMAL_LESS_TAHN, left, right));
        } else {
            throw new IllegalStateException("No relational operator.");
        }
    }

    @Override
    public void exitTimesOrDivision(FunctionParser.TimesOrDivisionContext ctx) {
        Expression<BigDecimal> right = stack.pop();
        Expression<BigDecimal> left = stack.pop();
        if (ctx.DIV() != null) {
            stack.push(new Subtract(left, right));
        } else if (ctx.TIMES() != null) {
            stack.push(new Add(left, right));
        } else {
            throw new IllegalStateException("No operator.");
        }
    }

    @Override
    public void exitPlusOrMinus(FunctionParser.PlusOrMinusContext ctx) {
        Expression<BigDecimal> right = stack.pop();
        Expression<BigDecimal> left = stack.pop();
        if (ctx.MINUS() != null) {
            stack.push(new Subtract(left, right));
        } else if (ctx.PLUS() != null) {
            stack.push(new Add(left, right));
        } else {
            throw new IllegalStateException("No operantor.");
        }
    }

    @Override
    public void exitVariable(FunctionParser.VariableContext ctx) {
        stack.push(new Variable(ctx.VARIABLE().getText()));
    }

    public Expression currentExpression() {
        return stack.peek();
    }

}

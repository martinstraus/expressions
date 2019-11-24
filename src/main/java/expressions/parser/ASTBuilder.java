/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.parser;

import expressions.ast.ArithmeticExpression;
import expressions.ast.BooleanExpression;
import expressions.ast.Expression;
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
    private static final BiFunction<BigDecimal, BigDecimal, Boolean> BIG_DECIMAL_GREATER_THAN = (a, b) -> a.compareTo(b) > 0;
    private static final BiFunction<BigDecimal, BigDecimal, Boolean> BIG_DECIMAL_LESS_THAN = (a, b) -> a.compareTo(b) < 0;
    private static final BiFunction<BigDecimal, BigDecimal, BigDecimal> BIG_DECIMAL_ADD = (a, b) -> a.add(b);
    private static final BiFunction<BigDecimal, BigDecimal, BigDecimal> BIG_DECIMAL_SUBTRACT = (a, b) -> a.subtract(b);
    private static final BiFunction<BigDecimal, BigDecimal, BigDecimal> BIG_DECIMAL_MULTIPLY = (a, b) -> a.multiply(b);
    private static final BiFunction<BigDecimal, BigDecimal, BigDecimal> BIG_DECIMAL_DIVIDE = (a, b) -> a.divide(b);
    private static final BiFunction<BigDecimal, BigDecimal, BigDecimal> BIG_DECIMAL_POWER = (a, b) -> a.pow(b.intValue());

    private final Stack<Expression> stack;

    public ASTBuilder() {
        this.stack = new Stack<>();
    }

    @Override
    public void exitPower(FunctionParser.PowerContext ctx) {
        Expression<BigDecimal> exponent = stack.pop();
        Expression<BigDecimal> base = stack.pop();
        stack.push(new ArithmeticExpression(BIG_DECIMAL_POWER, base, exponent));
    }

    @Override
    public void exitBooleanExpression(FunctionParser.BooleanExpressionContext ctx) {
        Expression right = stack.pop();
        Expression left = stack.pop();
        if (ctx.EQ() != null) {
            stack.add(new BooleanExpression(BIG_DECIMAL_EQUALS, left, right));
        } else if (ctx.GT() != null) {
            stack.add(new BooleanExpression(BIG_DECIMAL_GREATER_THAN, left, right));
        } else if (ctx.LT() != null) {
            stack.add(new BooleanExpression(BIG_DECIMAL_LESS_THAN, left, right));
        } else {
            throw new IllegalStateException("No relational operator.");
        }
    }

    @Override
    public void exitTimesOrDivision(FunctionParser.TimesOrDivisionContext ctx) {
        Expression<BigDecimal> right = stack.pop();
        Expression<BigDecimal> left = stack.pop();
        stack.push(new ArithmeticExpression(arithmeticOperator(ctx), left, right));
    }

    private BiFunction<BigDecimal, BigDecimal, BigDecimal> arithmeticOperator(FunctionParser.TimesOrDivisionContext ctx) {
        if (ctx.DIV() != null) {
            return BIG_DECIMAL_DIVIDE;
        } else if (ctx.TIMES() != null) {
            return BIG_DECIMAL_MULTIPLY;
        } else {
            throw new IllegalStateException("No operator.");
        }
    }

    @Override
    public void exitPlusOrMinus(FunctionParser.PlusOrMinusContext ctx) {
        Expression<BigDecimal> right = stack.pop();
        Expression<BigDecimal> left = stack.pop();
        stack.push(new ArithmeticExpression(arithmeticOperator(ctx), left, right));
    }

    private BiFunction<BigDecimal, BigDecimal, BigDecimal> arithmeticOperator(FunctionParser.PlusOrMinusContext ctx) {
        if (ctx.PLUS() != null) {
            return BIG_DECIMAL_ADD;
        } else if (ctx.MINUS() != null) {
            return BIG_DECIMAL_SUBTRACT;
        } else {
            throw new IllegalStateException("No operator.");
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

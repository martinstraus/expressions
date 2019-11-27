/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.parser;

import expressions.ast.ArithmeticExpression;
import expressions.ast.BooleanExpression;
import expressions.ast.Expression;
import expressions.ast.In;
import expressions.ast.Literal;
import expressions.ast.Negate;
import expressions.ast.Not;
import expressions.ast.Set;
import expressions.ast.Variable;
import expressions.parser.antlr4.FunctionBaseListener;
import expressions.parser.antlr4.FunctionLexer;
import expressions.parser.antlr4.FunctionParser;
import java.math.BigDecimal;
import java.util.HashSet;
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
    public void exitLiteral(FunctionParser.LiteralContext ctx) {
        stack.push(new Literal<BigDecimal>(new BigDecimal(ctx.NUMBER().getText())));
    }

    @Override
    public void exitVariable(FunctionParser.VariableContext ctx) {
        stack.push(new Variable(ctx.VARIABLE().getText()));
    }

    @Override
    public void exitSet(FunctionParser.SetContext ctx) {
        java.util.Set<Expression> set = new HashSet<>();
        for (int i = 0; i < ctx.expression().size(); i++) {
            set.add(stack.pop());
        }
        stack.push(new Set(set));
    }

    @Override
    public void exitIn(FunctionParser.InContext ctx) {
        Expression set = stack.pop();
        Expression value = stack.pop();
        stack.push(new In(value, set));
    }

    @Override
    public void exitUnary(FunctionParser.UnaryContext ctx) {
        if (ctx.prefix != null && ctx.prefix.getType() == FunctionLexer.MINUS) {
            stack.push(new Negate(stack.pop()));
        }
    }

    @Override
    public void exitNot(FunctionParser.NotContext ctx) {
        stack.push(new Not(stack.pop()));
    }

    @Override
    public void exitAndOr(FunctionParser.AndOrContext ctx) {
        Expression<Boolean> right = stack.pop();
        Expression<Boolean> left = stack.pop();
        stack.push(new BooleanExpression<Boolean>(operator(ctx), left, right));
    }
    
    private BiFunction<Boolean, Boolean, Boolean> operator(FunctionParser.AndOrContext ctx) {
        if (ctx.AND() != null) {
            return (Boolean a, Boolean b) -> a && b;
        } else if (ctx.OR() != null) {
            return (Boolean a, Boolean b) -> a || b;
        } else {
            throw new IllegalStateException("No boolean operator.");
        }
    }

    public Expression currentExpression() {
        return stack.peek();
    }

}

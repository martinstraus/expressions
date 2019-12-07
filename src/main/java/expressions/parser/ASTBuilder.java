/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.parser;

import expressions.ast.ArithmeticExpression;
import expressions.ast.BooleanComparison;
import expressions.ast.BooleanExpression;
import expressions.ast.Equals;
import expressions.ast.Expression;
import expressions.ast.File;
import expressions.ast.FunctionCall;
import expressions.ast.FunctionDefinition;
import expressions.ast.In;
import expressions.ast.Literal;
import expressions.ast.Negate;
import expressions.ast.Not;
import expressions.ast.ParameterDefinition;
import expressions.ast.Set;
import expressions.ast.Types;
import expressions.ast.Variable;
import expressions.parser.antlr4.FunctionBaseListener;
import expressions.parser.antlr4.FunctionLexer;
import expressions.parser.antlr4.FunctionParser;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiFunction;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author martinstraus
 */
public class ASTBuilder extends FunctionBaseListener {

    private static final BiFunction<BigDecimal, BigDecimal, BigDecimal> BIG_DECIMAL_ADD = (a, b) -> a.add(b);
    private static final BiFunction<BigDecimal, BigDecimal, BigDecimal> BIG_DECIMAL_SUBTRACT = (a, b) -> a.subtract(b);
    private static final BiFunction<BigDecimal, BigDecimal, BigDecimal> BIG_DECIMAL_MULTIPLY = (a, b) -> a.multiply(b);
    private static final BiFunction<BigDecimal, BigDecimal, BigDecimal> BIG_DECIMAL_DIVIDE = (a, b) -> a.divide(b);
    private static final BiFunction<BigDecimal, BigDecimal, BigDecimal> BIG_DECIMAL_POWER = (a, b) -> a.pow(b.intValue());

    private final Stack<Expression> stack;
    private final Map<String, FunctionDefinition> functions;
    private File file;

    public ASTBuilder() {
        this.stack = new Stack<>();
        this.functions = new HashMap<>();
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
            stack.add(new BooleanExpression(new Equals(), left, right));
        } else if (ctx.GT() != null) {
            stack.add(new BooleanComparison((l, r) -> l.compareTo(r) > 0, left, right));
        } else if (ctx.LT() != null) {
            stack.add(new BooleanComparison((l, r) -> l.compareTo(r) < 0, left, right));
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
        stack.push(interpretLiteral(ctx));
    }

    private Expression interpretLiteral(FunctionParser.LiteralContext ctx) {
        if (ctx.NUMBER() != null) {
            return new Literal<BigDecimal>(Types.NUMBER, new BigDecimal(ctx.NUMBER().getText()));
        } else if (ctx.STRING() != null) {
            return new Literal<String>(Types.STRING, removeQuotations(ctx.STRING().getText()));
        } else {
            throw new IllegalStateException("Unsupported literal.");
        }
    }

    private String removeQuotations(String text) {
        return text.substring(1, text.length() - 1);
    }

    @Override
    public void exitVariable(FunctionParser.VariableContext ctx) {
        stack.push(new Variable(Types.UNKNOWN, ctx.VARIABLE().getText()));
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

    @Override
    public void exitFunctionCallExpr(FunctionParser.FunctionCallExprContext ctx) {
        stack.push(new FunctionCall(ctx.functionName.getText(), pop(ctx.parameters.size()), functions));
    }

    private List<Expression> pop(int count) {
        List<Expression> list = new ArrayList(count);
        for (int i = 0; i < count; i++) {
            list.set(count - 1 - i, stack.pop());
        }
        return list;
    }

    @Override
    public void exitFunction(FunctionParser.FunctionContext ctx) {
        String functionName = ctx.functionName.getText();
        List<ParameterDefinition> parameters = ctx.parameters.stream().map((p) -> new ParameterDefinition(p.getText())).collect(toList());
        functions.put(
                functionName,
                new FunctionDefinition(functionName, parameters, stack.pop())
        );
    }

    @Override
    public void exitFile(FunctionParser.FileContext ctx) {
        file = new File(functions, stack.pop());
    }

    public File currentFile() {
        return file;
    }

}

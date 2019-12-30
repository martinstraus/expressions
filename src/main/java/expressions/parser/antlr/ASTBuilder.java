/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.parser.antlr;

import expressions.ast.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.function.BiFunction;
import static java.util.stream.Collectors.toList;
import static java.util.Collections.EMPTY_LIST;

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
    private final List<Statement> statements;
    private final List<FunctionDefinition> functions;
    private File file;

    public ASTBuilder() {
        this.stack = new Stack<>();
        this.functions = new ArrayList<>();
        this.statements = new ArrayList<>();
    }

    @Override
    public void exitBinaryOperation(FunctionParser.BinaryOperationContext ctx) {
        Expression right = stack.pop();
        Expression left = stack.pop();
        if (ctx.EQ() != null) {
            stack.add(new BooleanExpression(new Equals(), left, right));
        } else if (ctx.GT() != null) {
            stack.add(new BooleanComparison((l, r) -> l.compareTo(r) > 0, left, right));
        } else if (ctx.LT() != null) {
            stack.add(new BooleanComparison((l, r) -> l.compareTo(r) < 0, left, right));
        } else if (ctx.AND() != null) {
            stack.push(new BooleanExpression<Boolean>((Boolean a, Boolean b) ->a && b, left, right));
        } else if (ctx.OR() != null) {
            stack.push(new BooleanExpression<Boolean>((Boolean a, Boolean b) ->a || b, left, right));
        } else if (ctx.IN() != null) {
            stack.push(new In(left, right));
        } else if (ctx.POW() != null) {
            stack.push(new ArithmeticExpression(BIG_DECIMAL_POWER, left, right));
        }else if (ctx.DIV() != null) {
            stack.push(new ArithmeticExpression(BIG_DECIMAL_DIVIDE, left, right));
        } else if (ctx.TIMES() != null) {
            stack.push(new ArithmeticExpression(BIG_DECIMAL_MULTIPLY, left, right));
        } else if (ctx.PLUS() != null) {
            stack.push(new ArithmeticExpression(BIG_DECIMAL_ADD, left, right));
        } else if (ctx.MINUS() != null) {
            stack.push(new ArithmeticExpression(BIG_DECIMAL_SUBTRACT, left, right));
        } else {
            throw new IllegalStateException("No relational operator.");
        }
    }

    @Override
    public void exitValueLiteral(FunctionParser.ValueLiteralContext ctx) {
        stack.push(new Variable(Types.UNKNOWN, ctx.name.getText()));
    }

    @Override
    public void exitNumberLiteral(FunctionParser.NumberLiteralContext ctx) {
        stack.push(new Literal<>(Types.NUMBER, new BigDecimal(ctx.NUMBER().getText())));
    }

    @Override
    public void exitStringLiteral(FunctionParser.StringLiteralContext ctx) {
        stack.push(new Literal<>(Types.STRING, removeQuotations(ctx.STRING().getText())));
    }

    @Override
    public void exitDateLiteral(FunctionParser.DateLiteralContext ctx) {
        stack.push(new Literal<>(Types.DATE_UNIT, ctx.dateUnit().unit));
    }

    private String removeQuotations(String text) {
        return text.substring(1, text.length() - 1);
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
    public void exitUnaryOperation(FunctionParser.UnaryOperationContext ctx) {
        if (ctx.NOT() != null) {
            stack.push(new Not(stack.pop()));
        } else if (ctx.prefix != null && ctx.prefix.getType() == FunctionLexer.MINUS) {
            stack.push(new Negate(stack.pop()));
        }
    }

    @Override
    public void exitFunctionCall(FunctionParser.FunctionCallContext ctx) {
        stack.push(new FunctionCall(ctx.functionName.getText(), pop(ctx.parameters.size())));
    }

    private List<Expression> pop(int count) {
        List<Expression> list = new ArrayList(count);
        for (int i = 0; i < count; i++) {
            list.add(0, stack.pop()); // We add in index 0 to reverse the order of the popped elements.
        }
        return list;
    }

    @Override
    public void exitFunction(FunctionParser.FunctionContext ctx) {
        String functionName = ctx.name.getText();
        List<ParameterDefinition> parameters = ctx.parameters.stream().map((p) -> new ParameterDefinition(p.getText())).collect(toList());
        functions.add(new SimpleFunctionDefinition(functionName, parameters, stack.pop()));
    }

    @Override
    public void exitFile(FunctionParser.FileContext ctx) {
        file = new File(new ArrayList<>(functions), new ArrayList<>(statements), stack.pop());
        functions.clear();
        statements.clear();
    }

    @Override
    public void exitMap(FunctionParser.MapContext ctx) {
        stack.push(new Map(mapEntries(ctx)));
    }

    private List<Map.Entry> mapEntries(FunctionParser.MapContext ctx) {
        List<Map.Entry>entries = new ArrayList<>(ctx.entries.size());
        for (int i = 0; i < ctx.entries.size(); i++) {
            Expression value = stack.pop();
            Expression key = stack.pop();
            entries.add(new Map.Entry(key,value));  // The order is not important.
        }
        return entries;
    }

    @Override
    public void exitMapReference(FunctionParser.MapReferenceContext ctx) {
        Expression key = stack.pop();
        Expression<java.util.Map> value = stack.pop();
        stack.push(new MapReference(value, key));
    }

    @Override
    public void exitDefineValue(FunctionParser.DefineValueContext ctx) {
        statements.add(new DefineValue<>(ctx.name.getText(), stack.pop()));
    }

    public File currentFile() {
        return file;
    }

}

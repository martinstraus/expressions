package expressions.parser.antlr;

import expressions.ast.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import org.antlr.v4.runtime.Token;

/**
 *
 * @author martinstraus
 */
public class ASTBuilder extends FunctionBaseVisitor<Object> {

    private static final BiFunction<BigDecimal, BigDecimal, BigDecimal> BIG_DECIMAL_ADD = (a, b) -> a.add(b);
    private static final BiFunction<BigDecimal, BigDecimal, BigDecimal> BIG_DECIMAL_SUBTRACT = (a, b) -> a.subtract(b);
    private static final BiFunction<BigDecimal, BigDecimal, BigDecimal> BIG_DECIMAL_MULTIPLY = (a, b) -> a.multiply(b);
    private static final BiFunction<BigDecimal, BigDecimal, BigDecimal> BIG_DECIMAL_DIVIDE = (a, b) -> a.divide(b);
    private static final BiFunction<BigDecimal, BigDecimal, BigDecimal> BIG_DECIMAL_POWER = (a, b) -> a.pow(b.intValue());

    @Override
    public Object visitBinaryOperation(FunctionParser.BinaryOperationContext ctx) {
        Expression left = (Expression) ctx.left.accept(this);
        Expression right = (Expression) ctx.right.accept(this);
        if (ctx.EQ() != null) {
            return new BooleanExpression(new Equals(), left, right);
        } else if (ctx.GT() != null) {
            return new BooleanComparison((l, r) -> l.compareTo(r) > 0, left, right);
        } else if (ctx.LT() != null) {
            return new BooleanComparison((l, r) -> l.compareTo(r) < 0, left, right);
        } else if (ctx.AND() != null) {
            return new BooleanExpression<Boolean>((Boolean a, Boolean b) -> a && b, left, right);
        } else if (ctx.OR() != null) {
            return new BooleanExpression<Boolean>((Boolean a, Boolean b) -> a || b, left, right);
        } else if (ctx.IN() != null) {
            return new In(left, right);
        } else if (ctx.POW() != null) {
            return new ArithmeticExpression(BIG_DECIMAL_POWER, left, right);
        } else if (ctx.DIV() != null) {
            return new ArithmeticExpression(BIG_DECIMAL_DIVIDE, left, right);
        } else if (ctx.TIMES() != null) {
            return new ArithmeticExpression(BIG_DECIMAL_MULTIPLY, left, right);
        } else if (ctx.PLUS() != null) {
            return new ArithmeticExpression(BIG_DECIMAL_ADD, left, right);
        } else if (ctx.MINUS() != null) {
            return new ArithmeticExpression(BIG_DECIMAL_SUBTRACT, left, right);
        } else {
            throw new IllegalStateException("No relational operator.");
        }
    }

    @Override
    public Object visitValueLiteral(FunctionParser.ValueLiteralContext ctx) {
        return new Variable(Types.UNKNOWN, ctx.name.getText());
    }

    @Override
    public Object visitNumberLiteral(FunctionParser.NumberLiteralContext ctx) {
        return new Literal<>(Types.NUMBER, new BigDecimal(ctx.NUMBER().getText()));
    }

    @Override
    public Object visitStringLiteral(FunctionParser.StringLiteralContext ctx) {
        return new Literal<>(Types.STRING, removeQuotations(ctx.STRING().getText()));
    }

    @Override
    public Object visitDateLiteral(FunctionParser.DateLiteralContext ctx) {
        return new Literal<>(Types.DATE_UNIT, ctx.dateUnit().unit);
    }

    private String removeQuotations(String text) {
        return text.substring(1, text.length() - 1);
    }

    @Override
    public Object visitSet(FunctionParser.SetContext ctx) {
        java.util.Set<Expression> values = ctx.expression().stream().map((e) -> (Expression) e.accept(this)).collect(toSet());
        return new Set(values);
    }

    @Override
    public Object visitUnaryOperation(FunctionParser.UnaryOperationContext ctx) {
        if (ctx.NOT() != null) {
            return new Not((Expression) ctx.notValue.accept(this));
        } else if (ctx.atomValue != null) {
            Expression value = (Expression) ctx.atomValue.accept(this);
            return (ctx.prefix != null && ctx.prefix.getType() == FunctionLexer.MINUS)
                ? new Negate(value)
                : value;
        } else {
            throw new IllegalStateException("Cannot infer unary operation.");
        }
    }

    @Override
    public Object visitFunctionCall(FunctionParser.FunctionCallContext ctx) {
        List<Expression> parameters = ctx.parameters.stream().map((p) -> (Expression) p.accept(this)).collect(toList());
        return new FunctionCall(ctx.functionName.getText(), parameters);
    }

    @Override
    public Object visitFunction(FunctionParser.FunctionContext ctx) {
        String functionName = ctx.name.getText();
        List<ParameterDefinition> parameters = ctx.parameters.stream().map((p) -> new ParameterDefinition(p.getText())).collect(toList());
        List<Statement> statements = ctx.statements.stream().map((s) -> (Statement) s.accept(this)).collect(toList());
        Expression result = (Expression) ctx.expression().accept(this);
        return new SimpleFunctionDefinition<>(functionName, parameters, statements, result);
    }

    @Override
    public Object visitProgram(FunctionParser.ProgramContext ctx) {
        List<FunctionDefinition> functions = ctx.function().stream().map((f) -> (FunctionDefinition) f.accept(this)).collect(toList());
        List<Statement> statements = ctx.statements.stream().map((s) -> (Statement) s.accept(this)).collect(toList());
        Expression expression = (Expression) ctx.expression().accept(this);
        return new Program(functions, statements, expression);
    }

    @Override
    public Object visitMap(FunctionParser.MapContext ctx) {
        return new Map(mapEntries(ctx));
    }

    private List<Map.Entry> mapEntries(FunctionParser.MapContext ctx) {
        return ctx.entries.stream().map(this::mapEntry).collect(toList());
    }

    private Map.Entry mapEntry(FunctionParser.MapEntryContext entry) {
        return new Map.Entry(
            (Expression) entry.key.accept(this),
            (Expression) entry.value.accept(this)
        );
    }

    @Override
    public Object visitIndexedReference(FunctionParser.IndexedReferenceContext ctx) {
        Expression value = (Expression) ctx.value.accept(this);
        Expression key = (Expression) ctx.key.accept(this);
        return new IndexedReference(value, key);
    }

    @Override
    public Object visitDefineValue(FunctionParser.DefineValueContext ctx) {
        return new DefineValue(ctx.name.getText(), (Expression) ctx.expression().accept(this));
    }

    @Override
    public Object visitArray(FunctionParser.ArrayContext ctx) {
        return new Array(values(ctx));
    }

    private List<Expression> values(FunctionParser.ArrayContext ctx) {
        return ctx.expression().stream().map((v) -> (Expression) v.accept(this)).collect(toList());
    }

    @Override
    public Object visitPropertyReference(FunctionParser.PropertyReferenceContext ctx) {
        return new PropertyReference((Expression) ctx.expression().accept(this), properties(ctx));
    }

    @Override
    public Object visitNestedExpression(FunctionParser.NestedExpressionContext ctx) {
        return ctx.expression().accept(this);
    }

    private List<String> properties(FunctionParser.PropertyReferenceContext ctx) {
        return ctx.properties.stream().map(Token::getText).collect(toList());
    }
}

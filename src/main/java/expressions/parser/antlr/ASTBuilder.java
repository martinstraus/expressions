package expressions.parser.antlr;

import static expressions.Collections.foldList;
import static expressions.Collections.foldSet;
import expressions.ast.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import static java.util.stream.Collectors.toList;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class ASTBuilder extends LanguageBaseVisitor<Object> {

    private static final BiFunction<BigDecimal, BigDecimal, BigDecimal> BIG_DECIMAL_ADD = (a, b) -> a.add(b);
    private static final BiFunction<BigDecimal, BigDecimal, BigDecimal> BIG_DECIMAL_SUBTRACT = (a, b) -> a.subtract(b);
    private static final BiFunction<BigDecimal, BigDecimal, BigDecimal> BIG_DECIMAL_MULTIPLY = (a, b) -> a.multiply(b);
    private static final BiFunction<BigDecimal, BigDecimal, BigDecimal> BIG_DECIMAL_DIVIDE = (a, b) -> a.divide(b);
    private static final BiFunction<BigDecimal, BigDecimal, BigDecimal> BIG_DECIMAL_POWER = (a, b) -> a.pow(b.intValue());

    @Override
    public Object visitBinaryOperation(LanguageParser.BinaryOperationContext ctx) {
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
    public Object visitValueLiteral(LanguageParser.ValueLiteralContext ctx) {
        return new Variable(Types.UNKNOWN, ctx.name.getText());
    }

    @Override
    public Object visitNumberLiteral(LanguageParser.NumberLiteralContext ctx) {
        return new Literal<>(Types.NUMBER, new BigDecimal(ctx.NUMBER().getText()));
    }

    @Override
    public Object visitStringLiteral(LanguageParser.StringLiteralContext ctx) {
        return new Literal<>(Types.STRING, removeQuotations(ctx.STRING().getText()));
    }

    @Override
    public Object visitDateLiteral(LanguageParser.DateLiteralContext ctx) {
        return new Literal<>(Types.DATE_UNIT, ctx.dateUnit().unit);
    }

    private String removeQuotations(String text) {
        return text.substring(1, text.length() - 1);
    }

    @Override
    public Object visitSet(LanguageParser.SetContext ctx) {
        return new Set(foldExpressionsSet(ctx.expression()));
    }

    @Override
    public Object visitUnaryOperation(LanguageParser.UnaryOperationContext ctx) {
        if (ctx.NOT() != null) {
            return new Not((Expression) ctx.notValue.accept(this));
        } else if (ctx.atomValue != null) {
            Expression value = (Expression) ctx.atomValue.accept(this);
            return (ctx.prefix != null && ctx.prefix.getType() == LanguageLexer.MINUS)
                ? new Negate(value)
                : value;
        } else {
            throw new IllegalStateException("Cannot infer unary operation.");
        }
    }

    @Override
    public Object visitFunctionCall(LanguageParser.FunctionCallContext ctx) {
        return new FunctionCall(ctx.functionName.getText(), foldExpressionsList(ctx.parameters));
    }

    @Override
    public Object visitFunction(LanguageParser.FunctionContext ctx) {
        String functionName = ctx.name.getText();
        List<ParameterDefinition> parameters = foldList(ctx.parameters, p -> new ParameterDefinition(p.getText()));
        List<Statement> statements = foldList(ctx.statements, (s) -> (Statement) s.accept(this));
        Expression result = (Expression) ctx.expression().accept(this);
        return new SimpleFunctionDefinition<>(functionName, parameters, statements, result);
    }

    @Override
    public Object visitProgram(LanguageParser.ProgramContext ctx) {
        List<FunctionDefinition> functions = foldList(ctx.function(), (f) -> (FunctionDefinition) f.accept(this));
        List<Statement> statements = foldList(ctx.statements, (s) -> (Statement) s.accept(this));
        Expression expression = (Expression) ctx.expression().accept(this);
        return new Program(functions, statements, expression);
    }

    @Override
    public Object visitMap(LanguageParser.MapContext ctx) {
        return new Map(mapEntries(ctx));
    }

    private List<Map.Entry> mapEntries(LanguageParser.MapContext ctx) {
        return ctx.entries.stream().map(this::mapEntry).collect(toList());
    }

    private Map.Entry mapEntry(LanguageParser.MapEntryContext entry) {
        return new Map.Entry(
            (Expression) entry.key.accept(this),
            (Expression) entry.value.accept(this)
        );
    }

    @Override
    public Object visitIndexedReference(LanguageParser.IndexedReferenceContext ctx) {
        Expression value = (Expression) ctx.value.accept(this);
        Expression key = (Expression) ctx.key.accept(this);
        return new IndexedReference(value, key);
    }

    @Override
    public Object visitDefineValue(LanguageParser.DefineValueContext ctx) {
        return new DefineValue(ctx.name.getText(), (Expression) ctx.expression().accept(this));
    }

    @Override
    public Object visitArray(LanguageParser.ArrayContext ctx) {
        return new Array(values(ctx));
    }

    private List<Expression> values(LanguageParser.ArrayContext ctx) {
        return foldExpressionsList(ctx.expression());
    }

    @Override
    public Object visitPropertyReference(LanguageParser.PropertyReferenceContext ctx) {
        return new PropertyReference((Expression) ctx.expression().accept(this), properties(ctx));
    }

    @Override
    public Object visitNestedExpression(LanguageParser.NestedExpressionContext ctx) {
        return ctx.expression().accept(this);
    }

    @Override
    public Object visitBooleanLiteral(LanguageParser.BooleanLiteralContext ctx) {
        return new Literal<>(Types.BOOLEAN, ctx.booleanValue);
    }
    
    private List<String> properties(LanguageParser.PropertyReferenceContext ctx) {
        return foldList(ctx.properties, Token::getText);
    }

    private <T extends RuleContext> java.util.Set<Expression> foldExpressionsSet(Collection<T> collection) {
        return foldSet(collection, this::acceptExpression);
    }

    private <T extends RuleContext> java.util.List<Expression> foldExpressionsList(Collection<T> collection) {
        return foldList(collection, this::acceptExpression);
    }

    private Expression acceptExpression(RuleContext context) {
        return (Expression) context.accept(this);
    }
}

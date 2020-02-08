package expressions.parser.antlr;

import expressions.ast.Program;
import expressions.parser.Parser;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class AntlrParser implements Parser {

    @Override
    public Program parseProgram(String expression) {
        try {
            return parseProgram(CharStreams.fromReader(new StringReader(expression)));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Program parseProgram(Path path) {
        try {
            return parseProgram(CharStreams.fromPath(path));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Program parseProgram(CharStream charStream) {
        return (Program) parser(charStream).program().accept(new ASTBuilder());
    }

    private FunctionParser parser(CharStream charStream) {
        return new FunctionParser(new CommonTokenStream(new FunctionLexer(charStream)));
    }

}

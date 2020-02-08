package expressions.parser;

import expressions.ast.Program;
import java.nio.file.Path;
import org.antlr.v4.runtime.CharStream;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public interface Parser {

    Program parseProgram(String expression);

    Program parseProgram(CharStream charStream);

    Program parseProgram(Path path);
}

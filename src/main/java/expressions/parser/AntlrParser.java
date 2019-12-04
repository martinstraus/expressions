/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.parser;

import expressions.ast.File;
import expressions.parser.antlr4.FunctionLexer;
import expressions.parser.antlr4.FunctionParser;
import java.io.IOException;
import java.io.StringReader;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 *
 * @author martinstraus
 */
public class AntlrParser implements Parser {

    @Override
    public File parse(String expression) {
        try {
            FunctionParser parser = new FunctionParser(
                    new CommonTokenStream(
                            new FunctionLexer(
                                    CharStreams.fromReader(new StringReader(expression))
                            )
                    )
            );
            ASTBuilder extractor = new ASTBuilder();
            ParseTreeWalker.DEFAULT.walk(extractor, parser.file());
            return extractor.file();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}

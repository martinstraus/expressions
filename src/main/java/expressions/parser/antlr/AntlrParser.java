/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.parser.antlr;

import expressions.ast.File;
import expressions.parser.Parser;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import org.antlr.v4.runtime.CharStream;
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
            return parse(CharStreams.fromReader(new StringReader(expression)));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public File parse(Path path) {
        try {
            return parse(CharStreams.fromPath(path));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public File parse(CharStream charStream) {
        FunctionParser parser = new FunctionParser(new CommonTokenStream(new FunctionLexer(charStream)));
        ASTBuilder extractor = new ASTBuilder();
        ParseTreeWalker.DEFAULT.walk(extractor, parser.file());
        return extractor.currentFile();
    }

}

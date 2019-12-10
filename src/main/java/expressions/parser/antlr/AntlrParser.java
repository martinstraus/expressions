/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.parser.antlr;

import expressions.ast.File;
import expressions.ast.FunctionDefinition;
import expressions.evaluator.Date;
import expressions.evaluator.DateAdd;
import expressions.evaluator.DateSubtract;
import expressions.evaluator.Period;
import expressions.evaluator.Length;
import expressions.evaluator.Lowercase;
import expressions.evaluator.Matches;
import expressions.evaluator.Split;
import expressions.evaluator.Uppercase;
import expressions.parser.Parser;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
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
            Map<String, FunctionDefinition> functions = new HashMap<>();
            functions.put("matches", new Matches());
            functions.put("length", new Length());
            functions.put("split", new Split());
            functions.put("lowercase", new Lowercase());
            functions.put("uppercase", new Uppercase());
            functions.put("date", new Date());
            functions.put("period", new Period());
            functions.put("add", new DateAdd());
            functions.put("subtract", new DateSubtract());
            ASTBuilder extractor = new ASTBuilder(functions);
            ParseTreeWalker.DEFAULT.walk(extractor, parser.file());
            return extractor.currentFile();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}

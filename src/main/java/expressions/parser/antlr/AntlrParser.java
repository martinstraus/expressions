/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.parser.antlr;

import expressions.ast.File;
import expressions.ast.FunctionDefinition;
import expressions.evaluator.date.Date;
import expressions.evaluator.date.Add;
import expressions.evaluator.date.Subtract;
import expressions.evaluator.date.Period;
import expressions.evaluator.string.Length;
import expressions.evaluator.string.Lowercase;
import expressions.evaluator.string.Matches;
import expressions.evaluator.string.Split;
import expressions.evaluator.string.Uppercase;
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
            functions.put("add", new Add());
            functions.put("subtract", new Subtract());
            ASTBuilder extractor = new ASTBuilder(functions);
            ParseTreeWalker.DEFAULT.walk(extractor, parser.file());
            return extractor.currentFile();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}

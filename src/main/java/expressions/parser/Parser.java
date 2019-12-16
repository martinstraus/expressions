/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.parser;

import expressions.ast.File;
import java.nio.file.Path;
import org.antlr.v4.runtime.CharStream;

/**
 *
 * @author martinstraus
 */
public interface Parser {

    File parse(String expression);

    File parse(CharStream charStream);

    File parse(Path path);
}

package test.java;

import org.junit.Test;
import parser.execution.RoboExec;
import parser.lexical.tokenizer.Tokenizer;
import parser.syntax.nodes.ProgramNode;
import parser.syntax.parser.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by gregor on 07.09.16..
 */
public class TestExecutor {

    @Test
    public void TestModularity() throws IOException {
        String main = new String(Files.readAllBytes(Paths.get("src/test/resources/main.txt")));

        Tokenizer tokenizer = new Tokenizer(main);
        Parser parser = new Parser(tokenizer);

        ProgramNode pn = parser.getProgramNode();

        new RoboExec(pn).execute();
    }
}

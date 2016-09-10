import org.junit.Test;
import parser.execution.RoboExec;
import parser.lexical.Tokenizer;
import parser.syntax.nodes.ProgramNode;
import parser.syntax.parser.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by gregor on 07.09.16..
 */
public class TestExecutor {

//    @Test
//    public void TestDefinition(){
//        System.out.println("1.");
//        String program = "int a1, b1, c1 = 1, d1 = -2 + 5;" +
//                         "bool l = ! false && true;";
//        Tokenizer tokenizer = new Tokenizer(program);
//        Parser parser = new Parser(tokenizer);
//        ProgramNode pn = parser.getProgramNode();
//
//        new RoboExec(pn).execute();
//        System.out.println("--------------------------");
//    }
//
//    @Test
//    public void TestPrintFunction(){
//        System.out.println("2.");
//        String program ="double x = 10;" +
//                        "print x;";
//        Tokenizer tokenizer = new Tokenizer(program);
//        Parser parser = new Parser(tokenizer);
//        ProgramNode pn = parser.getProgramNode();
//
//        new RoboExec(pn).execute();
//        System.out.println("--------------------------");
//    }

//    @Test
//    public void TestReference(){
//        System.out.println("3.");
//        String program ="double x = 10;" +
//                "print x;";
//        Tokenizer tokenizer = new Tokenizer(program);
//        Parser parser = new Parser(tokenizer);
//        ProgramNode pn = parser.getProgramNode();
//
//        new RoboExec(pn).execute();
//        System.out.println("--------------------------");
//    }

    @Test
    public void TestModularity() throws IOException {
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        String main = new String(Files.readAllBytes(Paths.get("src/test/resources/main.txt")));

        Tokenizer tokenizer = new Tokenizer(main);
        Parser parser = new Parser(tokenizer);

        ProgramNode pn = parser.getProgramNode();

        new RoboExec(pn).execute();
    }
}

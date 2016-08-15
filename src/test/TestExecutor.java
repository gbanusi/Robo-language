package test;

import robo.parser.execution.RoboCompile;
import robo.parser.lexical.Tokenizer;
import robo.parser.syntax.Parser;
import robo.parser.syntax.nodes.ProgramNode;


public class TestExecutor {

	public static void main(String[] args) {
		String program = "int a, b;" +
				"a = 14;" +
				"if ( a >= 10 ) {" +
				" b = -1;" +
				"} else if (true) {" +
                " b = 1;" +
                "} else {" +
                " a = a * b -(-a);" +
                "}" +
				"print a, b, c;" +
				"while ( a >= 10 && b < 0) {" +
				"  a = a + b;" +
				"  print a, b;" +
				"}";

		Tokenizer tokenizer = new Tokenizer(program);
		Parser parser = new Parser(tokenizer);

		ProgramNode pn = parser.getProgramNode();

		new RoboCompile(pn).execute();
	}

}

package test;

import robo.parser.execution.RoboExec;
import robo.parser.lexical.Tokenizer;
import robo.parser.syntax.Parser;
import robo.parser.syntax.nodes.ProgramNode;


public class TestExecutor {

	public static void main(String[] args) {
		String program = "int a, b;" +
				"a = 14;" +
                "b = 2;" +
				"if ( a <= 10 ) {" +
				" b = -1;" +
				"} else if (false) {" +
                " b = 1;" +
                "} else {" +
                " a = a * b -(-a);" +
                "}" +
				"print a, b;" +
                "b = b * (-1);" +
                "print b;" +
				"do {" +
				"  a = a + b;" +
				"  print a, b;" +
				"} while ( a >= 40 && b < 0 &&  (a+b) != 38);" +
				"" +
				"function double kvadriraj(int x, int a){" +
				"  a = -100;" +
				"  return x*x;" +
				"}" +
				"print kvadriraj(a, b);" +
				"print b;";

		Tokenizer tokenizer = new Tokenizer(program);
		Parser parser = new Parser(tokenizer);

		ProgramNode pn = parser.getProgramNode();

		new RoboExec(pn).execute();
	}

}

package test;

import robo.parser.execution.RoboExec;
import robo.parser.lexical.Tokenizer;
import robo.parser.syntax.Parser;
import robo.parser.syntax.nodes.ProgramNode;


public class TestExecutor {

	public static void main(String[] args) {
		String program = "int a, b;" +
				"double const c;" +
				"string rijec;" +
				"rijec = \"Ovo radi ko satic mali!\";" +
				"a = 9;" +
                "b = 2;" +
				"c = 6;" +
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
				"  continue;" +
				"  print a, b;" +
				"} while ( a <= 40 && b < 0 &&  (a+b - c) != 38);" +
				"" +
				"function double kvadriraj(int x, int a){" +
				"  a = -100*2 + 1 * 3;" +
				"  " +
				"  return x*x;" +
				"}" +
				"print kvadriraj(1, c);" +
				"print b-10, c, rijec;";

		Tokenizer tokenizer = new Tokenizer(program);
		Parser parser = new Parser(tokenizer);

		ProgramNode pn = parser.getProgramNode();

		new RoboExec(pn).execute();
	}

}

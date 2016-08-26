package test;

import robo.parser.execution.RoboExec;
import robo.parser.lexical.Tokenizer;
import robo.parser.syntax.Parser;
import robo.parser.syntax.nodes.ProgramNode;


public class TestExecutor {

	public static void main(String[] args) {
		String program = "int a = 3, b;" +
				"double const c = 0.2;" +
				"double[2; 2] d = [[1+2, 5], [1-0.5, 1/10]]" +
				"string rijec;" +
				"rijec = \"Ovo radi ko satic mali!\";" +
				"a = 9;" +
                "b = 2.8;" +
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
				"function double kvadriraj(int x, double a){" +
				"  a = -100*2 + 1 * 3;" +
				"  " +
				"  return x*x;" +
				"}" +
				"print kvadriraj(1, c);" +
				"print d[1, 1];";

		String program2 = "double c;" +
				"c = 0.5;" +
				"int a;" +
				"a = 1;" +
				"c = c + a;" +
				"print a;";

		String program3 =
				"double[2; 2] d = [[1+2, 5], [1-0.5, 1/10]];" +
						"d[1; 0] = -1;" +
						"print d[0, 0], \" \", d[0, 1];" +
						"print d[1, 0], \" \", d[1, 1];";

		Tokenizer tokenizer = new Tokenizer(program3);
		Parser parser = new Parser(tokenizer);

		ProgramNode pn = parser.getProgramNode();

		new RoboExec(pn).execute();
	}

}

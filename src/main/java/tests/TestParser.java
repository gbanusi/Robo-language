package tests;

import parser.lexical.Tokenizer;
import parser.syntax.parser.Parser;


public class TestParser {

	public static void main(String[] args) {
		String program = "int const a = 10, b;" +
				"double[4, 4] d;" +
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
				"  a = x / 10 + 1 * 2;" +
				"  return x*x;" +
				"}" +
				"print kvadriraj(a, &b);";

		String program3 =
				"double[2; 2] d = [[1+2, 5], [1-0.5, 1/10]];" +
						"function double kvadriraj(int x, double[2; 2] a){" +
						"  a = a*a;" +
						"  return x*x;" +
						"}" +
						"print kvadriraj(1, d[1, 0]);"+
						"print d[0, 0], \" \", d[0, 1];"+
						"print d[1, 0], \" \", d[1, 1];";

		String prog = "bool b = ! false;" +
				"double[2; 2] d = [[1, 2], [3, 5]];" +
				"if(b){" +
				"  d[1;1] = 10;" +
				"}" +
				"print d[1,1];";


		Tokenizer tokenizer = new Tokenizer(prog);
		new Parser(tokenizer);
	}

}

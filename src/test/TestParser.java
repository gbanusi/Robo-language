package test;

import robo.parser.lexical.Tokenizer;
import robo.parser.syntax.Parser;


public class TestParser {

	public static void main(String[] args) {
		String program = "int const a, b;" +
				"a = 14;" +
				"b = 2;" +
//				"if ( a <= 10 ) {" +
//				" b = -1;" +
//				"} else if (false) {" +
//				" b = 1;" +
//				"} else {" +
//				" a = a * b -(-a);" +
//				"}" +
//				"print a, b;" +
//				"b = b * (-1);" +
//				"print b;" +
//				"do {" +
//				"  a = a + b;" +
//				"  print a, b;" +
//				"} while ( a >= 40 && b < 0 &&  (a+b) != 38);" +
				"" +
				"function double kvadriraj(int x, int a){" +
//				"  a = x / 10 + 1 * 2;" +
				"  return x*x;" +
				"}" +
				"print kvadriraj(a, &b);";

		Tokenizer tokenizer = new Tokenizer(program);
		new Parser(tokenizer);
	}

}

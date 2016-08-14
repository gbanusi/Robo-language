package test;

import robo.parser.lexical.Tokenizer;
import robo.parser.syntax.Parser;


public class TestParser {

	public static void main(String[] args) {
		String program = "int a, b;" +
				"a = 10;" +
				"if ( a >= 10 ) {" +
				" b = -1;" +
				"} else if (true) {" +
                " b = 1;" +
                "} else {" +
                " a = a * b -(-a);" +
                "}" +
				"print a, b, c;" +
                "function int kvadrat(int a, double b){" +
				" return 2;" +
				"}" +
				"print kvadrat(3);";

		String program2 =   "function int kvadrat(int a, double b){" +
							" return 2;" +
							"}";
		Tokenizer tokenizer = new Tokenizer(program2);
		new Parser(tokenizer);
	}

}

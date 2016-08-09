package test;

import robo.parser.lexical.Tokenizer;
import robo.parser.syntax.Parser;

/**
 * Demonstracija rada parsera.
 * 
 * @author marcupic
 */
public class TestParser {

	/**
	 * Metoda s kojom započinje izvođenje programa. Argumenti se ignoriraju.
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		String program = "int a, b;" +
				"a = 10;" +
				"if ( a >= 10 ) {" +
				" b = -1;" +
				"}" +
				"print a, b, c;";
		Tokenizer tokenizer = new Tokenizer(program);
		new Parser(tokenizer);
	}

}

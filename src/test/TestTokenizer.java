package test;

import robo.parser.lexical.TokenType;
import robo.parser.lexical.Tokenizer;

/**
 * Demonstracija rada tokenizatora.
 * 
 * @author marcupic
 */
public class TestTokenizer {

	/**
	 * Metoda s kojom započinje izvođenje programa. Argumenti se ignoriraju.
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		String program = "int a, b, c;\r\n" +
				"a = -2;\r\n" +
				"b = 1;" +
				"b = 6;\r\n" +
				"c = a - (-1 / 5 + ) - b;\r\n" +
				"if(a == b){ int a; a = b - 2; print a;}" +
				"print a;"
				;
		Tokenizer tokenizer = new Tokenizer(program);
		while(tokenizer.getCurrentToken().getTokenType()!= TokenType.EOF) {
			System.out.println("Trenutni peek: "+tokenizer.getCurrentToken().getTokenType()
					+", vrijednost '"+tokenizer.getCurrentToken().getValue()+"'");
			tokenizer.nextToken();
		}
	}

}

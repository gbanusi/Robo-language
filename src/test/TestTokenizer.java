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
     *
     * @param args argumenti naredbenog retka
     */
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
                "print a, b, c;";
        String program2 =   "function int kvadrat(int a, double b){" +
                            " return 2;" +
                            "}";
        Tokenizer tokenizer = new Tokenizer(program2);
        while (tokenizer.getCurrentToken().getTokenType() != TokenType.EOF) {
            System.out.println("Trenutni peek: " + tokenizer.getCurrentToken().getTokenType()
                    + ", vrijednost '" + tokenizer.getCurrentToken().getValue() + "'");
            tokenizer.nextToken();
        }
    }

}

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
                "print kvadriraj(a, &b);" +
                "print a, b;";

        Tokenizer tokenizer = new Tokenizer(program);
        while (tokenizer.getCurrentToken().getTokenType() != TokenType.EOF) {
            System.out.println("Trenutni peek: " + tokenizer.getCurrentToken().getTokenType()
                    + ", vrijednost '" + tokenizer.getCurrentToken().getValue() + "'");
            tokenizer.nextToken();
        }
    }

}

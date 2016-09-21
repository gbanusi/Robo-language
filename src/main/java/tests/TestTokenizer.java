package tests;

import parser.lexical.tokenizer.TokenType;
import parser.lexical.tokenizer.Tokenizer;

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
                "print kvadriraj(a, &b);" +
                "print a, b;";

        String prog = "bool b = ! false;" +
                "double[2, 2] d = [[1, 2], [3, 5]]" +
                "if(b){" +
                "  d[1;1] = 1;" +
                "}" +
                "print d[1;1];";

        Tokenizer tokenizer = new Tokenizer(prog);
        while (tokenizer.getCurrentToken().getTokenType() != TokenType.EOF) {
            System.out.println("Trenutni peek: " + tokenizer.getCurrentToken().getTokenType()
                    + ", vrijednost '" + tokenizer.getCurrentToken().getValue() + "'");
            tokenizer.nextToken();
        }
    }

}

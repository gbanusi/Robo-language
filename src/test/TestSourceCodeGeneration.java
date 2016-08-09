package test;

import robo.parser.lexical.Tokenizer;
import robo.parser.syntax.Parser;
import robo.parser.syntax.nodes.ProgramNode;

/**
 * Program koji predstavlja demonstraciju generiranja
 * izvornog koda programa temeljem sintaksnog stabla.
 * 
 * @author marcupic
 */
public class TestSourceCodeGeneration {

	/**
	 * Metoda s kojom započinje izvođenje programa. Argumenti 
	 * se ignoriraju.
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		String program = "def a, b: vector;\r\n" +
				"def c: vector;\r\n" +
				"let a = [1.5, 2.8];\r\n" +
				"let b = [2, 5];\r\n" +
				"let c = a - (b + [1.2,1]);\r\n" +
				"print a, b, c;"
				;
		
		Tokenizer tokenizer = new Tokenizer(program);
		Parser parser = new Parser(tokenizer);
		
		ProgramNode programNode = parser.getProgramNode();
		
//		ProgramPrintVisitor printer = new ProgramPrintVisitor();
//		programNode.accept(printer);
//		String sourceCode = printer.getSourceCode();
		
//		System.out.println(sourceCode);
	}

}

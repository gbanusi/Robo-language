package parser.syntax.nodes.statements;

import parser.execution.visitor.statement.NodeVisitor;
import parser.lexical.TokenType;
import parser.syntax.SyntaxException;
import parser.syntax.nodes.Node;
import parser.syntax.nodes.expression.NodeExpression;
import parser.syntax.parser.ParserHelper;

import java.util.ArrayList;
import java.util.List;


public class PrintStatement extends Node {

	private List<NodeExpression> expressions;

	public PrintStatement(List<NodeExpression> list) {
		super();
		this.expressions = new ArrayList<>(list);
	}

	public List<NodeExpression> getExpressions() {
		return new ArrayList<>(expressions);
	}

	public static Node parsePrint(ParserHelper parserHelper) {
		parserHelper.match(TokenType.PRINT);
		List<NodeExpression> list = parserHelper.parseExpressionsArray(TokenType.COMMA);
		if (! parserHelper.match(TokenType.SEMICOLON)) {
			throw new SyntaxException("Semicolon was expected.");
		}
		return new PrintStatement(list);
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
}

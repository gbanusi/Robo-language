package parser.syntax.nodes.statements;

import parser.execution.visitor.statement.NodeVisitor;
import parser.lexical.TokenType;
import parser.syntax.nodes.Node;
import parser.syntax.nodes.expression.NodeExpression;
import parser.syntax.parser.Parser;
import parser.syntax.parser.ParserHelper;

import java.util.ArrayList;
import java.util.List;


public class WhileStatement extends Node implements LoopStatement{

	private List<Node> statements = new ArrayList<>();

	private NodeExpression expression;

	public WhileStatement(List<Node> statements, NodeExpression expression) {
		super();
		this.statements=statements;
		this.expression = expression;
	}

	public List<Node> getStatements() {
		return statements;
	}

	public NodeExpression getExpression() {
		return expression;
	}

	public static Node parseWhile(ParserHelper parserHelper, Parser parser) {
		parserHelper.match(TokenType.WHILE);
		List<Node> statements;
		parserHelper.match(TokenType.OPEN_PARENTHESES);
		NodeExpression cond = parserHelper.parseValue();
		parserHelper.match(TokenType.CLOSED_PARENTHESES);
		parserHelper.match(TokenType.OPEN_CURLY);
		statements = parser.parse();
		return new WhileStatement(statements, cond);
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
}

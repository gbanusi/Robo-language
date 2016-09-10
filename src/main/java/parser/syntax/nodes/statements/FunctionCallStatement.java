package parser.syntax.nodes.statements;

import parser.execution.visitor.statement.NodeVisitor;
import parser.lexical.TokenType;
import parser.syntax.nodes.Node;
import parser.syntax.nodes.expression.NodeExpression;
import parser.syntax.parser.ParserHelper;


public class FunctionCallStatement extends Node {

	private NodeExpression expression;

	public FunctionCallStatement(NodeExpression funcNode) {
		super();
		this.expression = funcNode;
	}

	public NodeExpression getExpression() {
		return expression;
	}

	public static Node parseFunctionCall(ParserHelper parserHelper, String name) {
        // throw '('
        parserHelper.match(TokenType.OPEN_PARENTHESES);
		FunctionCallStatement fcs = new FunctionCallStatement(parserHelper.parseFuncCall(name));
        parserHelper.match(TokenType.SEMICOLON);
        return fcs;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
}

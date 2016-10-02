package parser.syntax.nodes.statements.function;

import parser.execution.visitor.statement.NodeVisitor;
import parser.lexical.tokenizer.TokenType;
import parser.syntax.nodes.Node;
import parser.syntax.nodes.expression.NodeExpression;
import parser.syntax.parser.ParserHelper;


public class FunctionCallStatement extends Node {

	private NodeExpression expression;

	private String funcName;

	public FunctionCallStatement(NodeExpression funcNode, String name) {
		super();
		this.expression = funcNode;
		this.funcName = name;
	}

	public NodeExpression getExpression() {
		return expression;
	}

	public String getFuncName() {
		return funcName;
	}

	public static Node parseFunctionCall(ParserHelper parserHelper, String name) {
        parserHelper.match(TokenType.OPEN_PARENTHESES);
		FunctionCallStatement fcs = new FunctionCallStatement(parserHelper.parseFuncCall(name), name);
        parserHelper.match(TokenType.SEMICOLON);
        return fcs;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
}

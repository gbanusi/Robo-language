package robo.parser.syntax.nodes.statements;

import robo.parser.execution.visitor.statement.NodeVisitor;
import robo.parser.syntax.nodes.Node;
import robo.parser.syntax.nodes.expression.NodeExpression;

import java.util.ArrayList;
import java.util.List;


public class DoStatement extends Node implements LoopStatement{

	private List<Node> statements = new ArrayList<>();

	private NodeExpression expression;

	public DoStatement(List<Node> statements, NodeExpression expression) {
		super();
		this.statements=statements;
		this.expression = expression;
	}

	public NodeExpression getExpression() {
		return expression;
	}

	public List<Node> getStatements() {
		return statements;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
}

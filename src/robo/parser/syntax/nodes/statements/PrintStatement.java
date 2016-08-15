package robo.parser.syntax.nodes.statements;

import robo.parser.execution.visitor.node.NodeVisitor;
import robo.parser.syntax.nodes.Node;
import robo.parser.syntax.nodes.expression.NodeExpression;

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

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
}

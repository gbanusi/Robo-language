package robo.parser.syntax.nodes.statements;

import robo.parser.syntax.nodes.Node;
import robo.parser.syntax.nodes.value.NodeExpression;

import java.util.ArrayList;
import java.util.List;


public class DoStatement extends Node {

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

//	@Override
//	public void accept(VLangNodeVisitor visitor) {
//		visitor.visit(this);
//	}
}

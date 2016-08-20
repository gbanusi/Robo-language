package robo.parser.syntax.nodes.expression;

import robo.parser.execution.visitor.expression.ExpressionNodeVisitor;

/**
 * Created by gregor on 10.08.16..
 */
public class NodeExpressionUnReference extends NodeExpressionUnaryOper{

	private String varName;

    public NodeExpressionUnReference(NodeExpression first) {
        super(first);
		this.varName = ((NodeVariable) first).getVarName();
    }

	public String getVarName() {
		return varName;
	}

	@Override
	public void accept(ExpressionNodeVisitor visitor) {
		visitor.visit(this);
	}
}

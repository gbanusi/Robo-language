package parser.syntax.nodes.expression;

import parser.execution.visitor.expression.ExpressionNodeVisitor;

/**
 * Created by gregor on 10.08.16..
 */
public class NodeExpressionUnReference extends NodeExpressionUnaryOper{


    public NodeExpressionUnReference(NodeExpression first) {
        super(first);
    }

	@Override
	public void accept(ExpressionNodeVisitor visitor) {
		visitor.visit(this);
	}
}

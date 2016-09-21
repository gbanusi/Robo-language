package parser.syntax.nodes.expression.operations;

import parser.execution.visitor.expression.ExpressionNodeVisitor;
import parser.syntax.nodes.expression.NodeExpression;
import parser.syntax.nodes.expression.NodeExpressionUnaryOper;

/**
 * Created by gregor on 10.08.16..
 */
public class NodeExpressionUnMinus extends NodeExpressionUnaryOper {


    public NodeExpressionUnMinus(NodeExpression first) {
        super(first);
    }

	@Override
	public void accept(ExpressionNodeVisitor visitor) {
		visitor.visit(this);
	}
}

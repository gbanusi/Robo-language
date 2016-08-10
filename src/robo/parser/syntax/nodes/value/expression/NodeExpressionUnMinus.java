package robo.parser.syntax.nodes.value.expression;

import robo.parser.syntax.nodes.value.NodeExpression;

/**
 * Created by gregor on 10.08.16..
 */
public class NodeExpressionUnMinus extends NodeExpressionUnaryOper{


    public NodeExpressionUnMinus(NodeExpression first) {
        super(first);
    }

//	@Override
//	public void accept(ExpressionNodeVisitor visitor) {
//		visitor.visit(this);
//	}
}

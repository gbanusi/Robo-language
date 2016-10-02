package parser.syntax.nodes.expression.operations;

import parser.execution.visitor.expression.ExpressionNodeVisitor;
import parser.syntax.nodes.expression.NodeExpression;
import parser.syntax.nodes.expression.NodeExpressionBinaryOper;

/**
 * Created by gregor on 02.10.16..
 */
public class NodeExpressionDot extends NodeExpressionBinaryOper {

    public NodeExpressionDot(NodeExpression first, NodeExpression second) {
        super(first, second);
    }

    @Override
    public void accept(ExpressionNodeVisitor visitor) {
        visitor.visit(this);
    }
}

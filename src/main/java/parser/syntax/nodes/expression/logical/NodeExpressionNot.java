package parser.syntax.nodes.expression.logical;

import parser.execution.visitor.expression.ExpressionNodeVisitor;
import parser.syntax.nodes.expression.NodeExpression;
import parser.syntax.nodes.expression.NodeExpressionUnaryOper;

/**
 * Created by gregor on 06.09.16..
 */
public class NodeExpressionNot extends NodeExpressionUnaryOper {


    public NodeExpressionNot(NodeExpression first) {
        super(first);
    }

    @Override
    public void accept(ExpressionNodeVisitor visitor) {
        visitor.visit(this);
    }
}
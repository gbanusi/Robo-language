package parser.syntax.nodes.expression;

import parser.execution.visitor.expression.ExpressionNodeVisitor;

/**
 * Created by gregor on 06.09.16..
 */
public class NodeExpressionNot extends NodeExpressionUnaryOper{


    public NodeExpressionNot(NodeExpression first) {
        super(first);
    }

    @Override
    public void accept(ExpressionNodeVisitor visitor) {
        visitor.visit(this);
    }
}
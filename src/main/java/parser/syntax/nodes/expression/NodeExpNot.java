package parser.syntax.nodes.expression;

import parser.execution.visitor.expression.ExpressionNodeVisitor;

/**
 * Created by gregor on 06.09.16..
 */
public class NodeExpNot extends NodeExpressionUnaryOper{


    public NodeExpNot(NodeExpression first) {
        super(first);
    }

    @Override
    public void accept(ExpressionNodeVisitor visitor) {
        visitor.visit(this);
    }
}
package robo.parser.syntax.nodes.expression;

import robo.parser.execution.visitor.expression.ExpressionNodeVisitor;

/**
 * Created by gregor on 07.08.16..
 */
public class NodeExpressionMult extends NodeExpressionBinaryOper {

    /**
     * Konstruktor objekta koji predstavlja <tt>first + second</tt>.
     * @param first prvi izraz
     * @param second drugi izraz
     */
    public NodeExpressionMult(NodeExpression first, NodeExpression second) {
        super(first, second);
    }

    @Override
    public void accept(ExpressionNodeVisitor visitor) {
        visitor.visit(this);
    }
}

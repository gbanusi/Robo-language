package parser.syntax.nodes.expression;

import parser.execution.visitor.expression.ExpressionNodeVisitor;

/**
 * Created by gregor on 10.08.16..
 */
public class NodeExpressionLERelation extends NodeExpressionBinaryOper {

    /**
     * Konstruktor objekta koji predstavlja <tt>first + second</tt>.
     *
     * @param first  prvi izraz
     * @param second drugi izraz
     */
    public NodeExpressionLERelation(NodeExpression first, NodeExpression second) {
        super(first, second);
    }


    @Override
    public void accept(ExpressionNodeVisitor visitor) {
        visitor.visit(this);
    }
}

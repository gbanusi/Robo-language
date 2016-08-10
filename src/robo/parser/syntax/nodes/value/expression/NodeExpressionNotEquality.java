package robo.parser.syntax.nodes.value.expression;

import robo.parser.syntax.nodes.value.NodeExpression;

/**
 * Created by gregor on 10.08.16..
 */
public class NodeExpressionNotEquality extends NodeExpressionBinaryOper {

    /**
     * Konstruktor objekta koji predstavlja <tt>first + second</tt>.
     * @param first prvi izraz
     * @param second drugi izraz
     */
    public NodeExpressionNotEquality(NodeExpression first, NodeExpression second) {
        super(first, second);
    }

//	@Override
//	public void accept(ExpressionNodeVisitor visitor) {
//		visitor.visit(this);
//	}
}

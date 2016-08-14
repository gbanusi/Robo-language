package robo.parser.syntax.nodes.value.expression;

import robo.parser.syntax.nodes.value.NodeExpression;
import robo.parser.syntax.nodes.visitor.ExpressionNodeVisitor;

/**
 * Created by gregor on 10.08.16..
 */
public class NodeExpressionNoEquality extends NodeExpressionBinaryOper {

    /**
     * Konstruktor objekta koji predstavlja <tt>first + second</tt>.
     * @param first prvi izraz
     * @param second drugi izraz
     */
    public NodeExpressionNoEquality(NodeExpression first, NodeExpression second) {
        super(first, second);
    }

	@Override
	public void accept(ExpressionNodeVisitor visitor) {
		visitor.visit(this);
	}
}

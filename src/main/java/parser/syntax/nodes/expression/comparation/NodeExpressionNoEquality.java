package parser.syntax.nodes.expression.comparation;

import parser.execution.visitor.expression.ExpressionNodeVisitor;
import parser.syntax.nodes.expression.NodeExpression;
import parser.syntax.nodes.expression.NodeExpressionBinaryOper;

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

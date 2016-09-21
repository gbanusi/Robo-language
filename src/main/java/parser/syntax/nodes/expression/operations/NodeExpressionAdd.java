package parser.syntax.nodes.expression.operations;

import parser.execution.visitor.expression.ExpressionNodeVisitor;
import parser.syntax.nodes.expression.NodeExpression;
import parser.syntax.nodes.expression.NodeExpressionBinaryOper;

/**
 * Model izraza koji predstavlja operator <i>add</i>, odnosno
 * zbrajanje dvaju drugih izraza.
 * 
 * @author marcupic
 */
public class NodeExpressionAdd extends NodeExpressionBinaryOper {

	/**
	 * Konstruktor objekta koji predstavlja <tt>first + second</tt>.
	 * @param first prvi izraz
	 * @param second drugi izraz
	 */
	public NodeExpressionAdd(NodeExpression first, NodeExpression second) {
		super(first, second);
	}

	@Override
	public void accept(ExpressionNodeVisitor visitor) {
		visitor.visit(this);
	}
}

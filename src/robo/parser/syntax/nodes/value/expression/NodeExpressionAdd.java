package robo.parser.syntax.nodes.value.expression;

import robo.parser.syntax.nodes.value.NodeExpression;

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

//	@Override
//	public void accept(ExpressionNodeVisitor visitor) {
//		visitor.visit(this);
//	}
}

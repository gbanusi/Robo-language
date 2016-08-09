package robo.parser.syntax.nodes.operations;

/**
 * Model izraza koji predstavlja operator <i>sub</i>, odnosno
 * oduzimanje dvaju drugih izraza.
 * 
 * @author marcupic
 */
public class NodeExpressionSub extends NodeExpressionBinaryOper {

	/**
	 * Konstruktor objekta koji predstavlja <tt>first - second</tt>.
	 * @param first prvi izraz
	 * @param second drugi izraz
	 */
	public NodeExpressionSub(NodeExpression first, NodeExpression second) {
		super(first, second);
	}

//	@Override
//	public void accept(ExpressionNodeVisitor visitor) {
//		visitor.visit(this);
//	}

}

package robo.parser.syntax.nodes.expression;

/**
 * Model izraza koji predstavlja neki binarni operator, odnosno
 * operator koji djeluje nad dva druga izraza.
 * 
 * @author marcupic
 */
public abstract class NodeExpressionUnaryOper extends NodeExpression {

	/**
	 * Prvi izraz.
	 */
	private NodeExpression first;


	/**
	 * Konstruktor objekta koji računa <tt>first OPER second</tt> gdje je
	 * <i>OPER</i> neki binarni (u ovom trenutku još uvijek nepoznati)
	 * operator.
	 * @param first prvi izraz
	 * @param second drugi izraz
	 */
	public NodeExpressionUnaryOper(NodeExpression first) {
		this.first = first;
	}

	/**
	 * Dohvat prvog (lijevog) izraza.
	 * @return prvi izraz
	 */
	public NodeExpression getFirst() {
		return first;
	}

}

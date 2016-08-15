package robo.parser.syntax.nodes.expression;

/**
 * Model izraza koji predstavlja neki binarni operator, odnosno
 * operator koji djeluje nad dva druga izraza.
 * 
 * @author marcupic
 */
public abstract class NodeExpressionBinaryOper extends NodeExpression {
	
	/**
	 * Prvi izraz.
	 */
	private NodeExpression first;
	/**
	 * Drugi izraz.
	 */
	private NodeExpression second;
	
	/**
	 * Konstruktor objekta koji računa <tt>first OPER second</tt> gdje je
	 * <i>OPER</i> neki binarni (u ovom trenutku još uvijek nepoznati) 
	 * operator.
	 * @param first prvi izraz
	 * @param second drugi izraz
	 */
	public NodeExpressionBinaryOper(NodeExpression first, NodeExpression second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * Dohvat prvog (lijevog) izraza.
	 * @return prvi izraz
	 */
	public NodeExpression getFirst() {
		return first;
	}
	
	/**
	 * Dohvat drugog (desnog) izraza.
	 * @return drugi izraz
	 */
	public NodeExpression getSecond() {
		return second;
	}

}

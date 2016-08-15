package robo.parser.syntax.nodes.expression;

import robo.parser.Vector;
import robo.parser.execution.visitor.expression.ExpressionNodeVisitor;

/**
 * Model izraza koji predstavlja vektorsku konstantu.
 * 
 * @author marcupic
 */
public class NodeVector extends NodeExpression{

	/**
	 * Vektorska konstanta.
	 */
	private Vector vector;
	
	/**
	 * Konstruktor.
	 * @param vector vektorska konstanta
	 */
	public NodeVector(Vector vector) {
		this.vector = vector;
	}

	/**
	 * Dohvat vrijednosti vektorske konstante.
	 * @return vektorska konstanta
	 */
	public Vector getVector() {
		return vector;
	}
	
	@Override
	public void accept(ExpressionNodeVisitor visitor) {
		visitor.visit(this);
	}
	
}

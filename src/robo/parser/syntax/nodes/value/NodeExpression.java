package robo.parser.syntax.nodes.value;

import robo.parser.syntax.nodes.visitor.ExpressionNodeVisitor;

/**
 * Vršni razred koji predstavlja bilo kakav izraz.
 * 
 * @author marcupic
 */
public abstract class NodeExpression {

	/**
	 * Konstruktor.
	 */
	public NodeExpression() {
	}

	/**
	 * Apstraktna metoda koja prihvaća posjetitelja izraza.
	 * 
	 * @param visitor posjetitelj
	 */
	public abstract void accept(ExpressionNodeVisitor visitor);
	
}

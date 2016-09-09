package parser.syntax.nodes.expression;

import parser.execution.visitor.expression.ExpressionNodeVisitor;

/**
 * Model izraza koji predstavlja varijablu.
 * 
 * @author marcupic
 */
public class NodeVariable extends NodeExpression implements LeftValue {

	/**
	 * Naziv varijable.
	 */
	private String varName;
	
	/**
	 * Konstruktor.
	 * @param varName naziv varijable
	 */
	public NodeVariable(String varName) {
		this.varName = varName;
	}

	/**
	 * Dohvat naziva varijable.
	 * @return naziv varijable
	 */
	public String getVarName() {
		return varName;
	}
	
	@Override
	public void accept(ExpressionNodeVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String getVariableName() {
		return varName;
	}
}

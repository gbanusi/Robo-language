package robo.parser.syntax.nodes.types;

import robo.parser.lexical.Type;

/**
 * Model izraza koji predstavlja varijablu.
 * 
 * @author marcupic
 */
public class NodeVariable {

	/**
	 * Naziv varijable.
	 */
	private String varName;

	private Type type;
	
	/**
	 * Konstruktor.
	 * @param varName naziv varijable
	 */
	public NodeVariable(String varName, Type type) {
		this.varName = varName;
		this.type = type;
	}

	/**
	 * Dohvat naziva varijable.
	 * @return naziv varijable
	 */
	public String getVarName() {
		return varName;
	}
	
//	@Override
//	public void accept(ExpressionNodeVisitor visitor) {
//		visitor.visit(this);
//	}
}

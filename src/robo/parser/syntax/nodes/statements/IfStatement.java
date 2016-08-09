package robo.parser.syntax.nodes.statements;

import robo.parser.syntax.nodes.Node;
import robo.parser.syntax.nodes.operations.NodeExpression;

import java.util.ArrayList;
import java.util.List;


public class IfStatement extends Node {

	private List<Node> statements = new ArrayList<>();

	private NodeExpression expression;
	
	/**
	 * Konstruktor.
	 * @param varName naziv varijable kojoj se dodjeljuje vrijednost
	 * @param expression izraz koji treba izračunati
	 */
	public IfStatement(String varName, NodeExpression expression) {
		super();
		this.expression = expression;
	}
	
	/**
	 * Dohvat izraza koji treba izračunati.
	 * @return izraz
	 */
	public NodeExpression getExpression() {
		return expression;
	}

//	@Override
//	public void accept(VLangNodeVisitor visitor) {
//		visitor.visit(this);
//	}
}

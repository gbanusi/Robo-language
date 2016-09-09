package parser.syntax.nodes;

import parser.execution.visitor.statement.NodeVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Model slijeda naredbi programa napisanog jezikom 
 * <i>vlang</i>.
 * 
 * @author marcupic
 */
public class ProgramNode extends Node {

	/**
	 * Slijed nareebi.
	 */
	private List<Node> statements;
	
	/**
	 * Konstruktor.
	 * @param statements slijed naredbi
	 */
	public ProgramNode(List<Node> statements) {
		this.statements = new ArrayList<>(statements);
	}

	/**
	 * Dohvat slijeda naredbi.
	 * @return slijed naredbi
	 */
	public List<Node> getStatements() {
		return new ArrayList<>(statements);
	}
	
	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
}

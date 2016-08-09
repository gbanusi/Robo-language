package robo.parser.syntax.nodes.statements;

import robo.parser.syntax.nodes.Node;
import robo.parser.syntax.nodes.operations.NodeExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Model naredbe "print" jezika <i>vlang</i>.
 * 
 * @author marcupic
 */
public class PrintStatement extends Node {

	/**
	 * Lista izraza koje treba ispisati.
	 */
	private List<NodeExpression> list;
	
	/**
	 * Konstruktor.
	 * @param list lista izraza koje treba ispisati
	 */
	public PrintStatement(List<NodeExpression> list) {
		super();
		this.list = new ArrayList<>(list);
	}

	/**
	 * Dohvat liste izraza koje treba ispisati
	 * @return
	 */
	public List<NodeExpression> getList() {
		return new ArrayList<>(list);
	}
	
//	@Override
//	public void accept(VLangNodeVisitor visitor) {
//		visitor.visit(this);
//	}
}

package parser.syntax.nodes.expression;

import parser.execution.visitor.expression.ExpressionNodeVisitor;

import java.util.List;

/**
 * Model izraza koji predstavlja varijablu.
 * 
 * @author marcupic
 */
public class NodeArrayIndexing extends NodeExpression implements LeftValue {

	/**
	 * Naziv varijable.
	 */
	private String varName;

	private List<NodeExpression> index;

	public NodeArrayIndexing(String varName, List<NodeExpression> index) {
		this.varName = varName;
		this.index = index;
	}


	/**
	 * Dohvat naziva varijable.
	 * @return naziv varijable
	 */
	public String getVarName() {
		return varName;
	}

	public List<NodeExpression> getIndex() {
		return index;
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

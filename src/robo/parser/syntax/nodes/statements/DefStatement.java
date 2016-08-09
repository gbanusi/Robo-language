package robo.parser.syntax.nodes.statements;

import robo.parser.lexical.Token;
import robo.parser.syntax.nodes.Node;
import robo.parser.syntax.nodes.types.NodeVariable;

import java.util.ArrayList;
import java.util.List;


public class DefStatement extends Node {

	/**
	 * Lista imena varijabli koje se definiraju.
	 */
	private List<Token> variables;

	/**
	 * Konstruktor.
	 * @param variables lista imena varijabli
	 */
	public DefStatement(List<Token> variables, List<NodeVariable> nv, VarEnvironment ivs) {
		this.variables = new ArrayList<>(variables);
		for(int i = 0; i < variables.size(); i++){
			ivs.put(variables.get(i), nv.get(i));
		}
	}

	/**
	 * Dohvat liste imena varijabli.
	 * @return lista imena varijabli
	 */
	public List<Token> getVariables() {
		return new ArrayList<>(variables);
	}

//	@Override
//	public void accept(VLangNodeVisitor visitor) {
//		visitor.visit(this);
//	}
}
package robo.parser.syntax.nodes.statements;

import robo.parser.syntax.nodes.Node;

import java.util.ArrayList;
import java.util.List;

public class IfBlockStatement extends Node {

    private List<Node> ifStatements = new ArrayList<>();

    public IfBlockStatement(List<Node> statements) {
        super();
        this.ifStatements=statements;
    }

//	@Override
//	public void accept(VLangNodeVisitor visitor) {
//		visitor.visit(this);
//	}
}

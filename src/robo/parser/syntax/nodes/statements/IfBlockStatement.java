package robo.parser.syntax.nodes.statements;

import robo.parser.execution.visitor.statement.NodeVisitor;
import robo.parser.syntax.nodes.Node;

import java.util.ArrayList;
import java.util.List;

public class IfBlockStatement extends Node {

    private List<Node> ifStatements = new ArrayList<>();

    public IfBlockStatement(List<Node> statements) {
        super();
        this.ifStatements=statements;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public List<Node> getIfStatements() {
        return ifStatements;
    }
}

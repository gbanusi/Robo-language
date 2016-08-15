package robo.parser.syntax.nodes.statements;

import robo.parser.execution.visitor.node.NodeVisitor;
import robo.parser.syntax.nodes.Node;
import robo.parser.syntax.nodes.expression.NodeExpression;

import java.util.ArrayList;
import java.util.List;


public class IfStatement extends Node {

    private List<Node> statements = new ArrayList<>();

    private NodeExpression expression;

    public IfStatement(List<Node> statements, NodeExpression expression) {
        super();
        this.statements = statements;
        this.expression = expression;
    }

    public NodeExpression getExpression() {
        return expression;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public List<Node> getStatements() {
        return statements;
    }
}

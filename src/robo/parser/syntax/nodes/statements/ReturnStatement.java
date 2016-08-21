package robo.parser.syntax.nodes.statements;

import robo.parser.execution.visitor.statement.NodeVisitor;
import robo.parser.syntax.nodes.Node;
import robo.parser.syntax.nodes.expression.NodeExpression;

/**
 * Created by gregor on 14.08.16..
 *
 * AKA Asign function
 */
public class ReturnStatement extends Node{

    private NodeExpression value;

    public ReturnStatement(NodeExpression value) {
        this.value = value;
    }

    public NodeExpression getValue() {
        return value;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}

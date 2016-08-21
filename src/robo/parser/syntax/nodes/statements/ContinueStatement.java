package robo.parser.syntax.nodes.statements;

import robo.parser.execution.visitor.statement.NodeVisitor;
import robo.parser.syntax.nodes.Node;

/**
 * Created by gregor on 13.08.16..
 */
public class ContinueStatement extends Node {

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}

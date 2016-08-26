package robo.parser.syntax.nodes.statements;


import robo.parser.execution.visitor.statement.NodeVisitor;
import robo.parser.syntax.nodes.Node;
import robo.parser.syntax.nodes.expression.NodeExpression;

import java.util.List;

/**
 * Created by gregor on 09.08.16..
 */
public class AssignArrayStatement extends Node {

    private String var;

    private List<NodeExpression> indexes;

    private NodeExpression expression;

    public AssignArrayStatement(String var, List<NodeExpression> indexes, NodeExpression expression) {
        super();
        this.var = var;
        this.indexes = indexes;
        this.expression = expression;
    }

    public String getVar() {
        return var;
    }

    public List<NodeExpression> getIndexes() {
        return indexes;
    }

    public NodeExpression getExpression() {
        return expression;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}

package robo.parser.syntax.nodes.statements;


import robo.parser.execution.visitor.statement.NodeVisitor;
import robo.parser.syntax.nodes.Node;
import robo.parser.syntax.nodes.expression.NodeExpression;

/**
 * Created by gregor on 09.08.16..
 */
public class AssignVarStatement extends Node {

    private String var;

    private NodeExpression expression;

    public AssignVarStatement(String var, NodeExpression expression) {
        super();
        this.var = var;
        this.expression = expression;
    }

    public String getVar() {
        return var;
    }

    public NodeExpression getExpression() {
        return expression;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}

package parser.syntax.nodes.expression.value.right;

import parser.execution.visitor.expression.ExpressionNodeVisitor;
import parser.syntax.nodes.expression.NodeExpression;

import java.util.List;

/**
 * Created by gregor on 14.08.16..
 */
public class NodeFunction extends NodeExpression {

    private List<NodeExpression> vars;

    private String fName;

    public NodeFunction(List<NodeExpression> vars, String fName) {
        this.vars = vars;
        this.fName = fName;
    }

    public List<NodeExpression> getVars() {
        return vars;
    }

    public String getfName() {
        return fName;
    }

    @Override
    public void accept(ExpressionNodeVisitor visitor) {
        visitor.visit(this);
    }
}

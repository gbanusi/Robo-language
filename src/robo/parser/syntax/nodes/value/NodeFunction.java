package robo.parser.syntax.nodes.value;

import robo.parser.syntax.nodes.visitor.ExpressionNodeVisitor;

import java.util.List;

/**
 * Created by gregor on 14.08.16..
 */
public class NodeFunction extends NodeExpression{

    private List<NodeExpression> vars;

    private String fName;

    public NodeFunction(List<NodeExpression> vars, String fName) {
        this.vars = vars;
        this.fName = fName;
    }


    @Override
    public void accept(ExpressionNodeVisitor visitor) {
        visitor.visit(this);
    }
}

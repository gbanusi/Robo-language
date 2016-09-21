package parser.syntax.nodes.expression.value.left;

import parser.execution.visitor.expression.ExpressionNodeVisitor;
import parser.lexical.type.Type;
import parser.syntax.nodes.expression.NodeExpression;

import java.util.List;

/**
 * Created by gregor on 26.08.16..
 */
public class NodeArray extends NodeExpression {


    /**
     * Naziv varijable.
     */
    private List<NodeExpression> value;

    private Type type;

    /**
     * Konstruktor.
     */
    public NodeArray(Type type, List<NodeExpression> value) {
        this.value = value;
        this.type = type;
    }

    public List<NodeExpression> getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    @Override
    public void accept(ExpressionNodeVisitor visitor) {
        visitor.visit(this);
    }

}

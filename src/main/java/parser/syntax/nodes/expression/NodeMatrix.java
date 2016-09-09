package parser.syntax.nodes.expression;

import parser.execution.visitor.expression.ExpressionNodeVisitor;
import parser.lexical.Type;

import java.util.List;

/**
 * Created by gregor on 26.08.16..
 */
public class NodeMatrix extends NodeExpression {


    /**
     * Naziv varijable.
     */
    private List<NodeExpression> value;

    private Type type;

    /**
     * Konstruktor.
     */
    public NodeMatrix(Type type, List<NodeExpression> value) {
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

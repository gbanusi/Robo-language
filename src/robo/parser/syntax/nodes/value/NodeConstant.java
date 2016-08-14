package robo.parser.syntax.nodes.value;

import robo.parser.lexical.Type;
import robo.parser.syntax.nodes.visitor.ExpressionNodeVisitor;

/**
 * Created by gregor on 07.08.16.
 */
public class NodeConstant extends NodeExpression {

    /**
     * Naziv varijable.
     */
    private Object value;

    private Type type;

    /**
     * Konstruktor.
     */
    public NodeConstant(Type type, Object value) {
        this.value = value;
        this.type = type;
    }

    public Object getValue() {
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

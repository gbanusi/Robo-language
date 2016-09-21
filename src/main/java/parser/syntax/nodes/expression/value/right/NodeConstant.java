package parser.syntax.nodes.expression.value.right;

import parser.execution.values.RoboValue;
import parser.lexical.type.Type;
import parser.execution.visitor.expression.ExpressionNodeVisitor;
import parser.syntax.nodes.expression.NodeExpression;

/**
 * Created by gregor on 07.08.16.
 */
public class NodeConstant extends NodeExpression {

    /**
     * Naziv varijable.
     */
    private RoboValue value;

    private Type type;

    /**
     * Konstruktor.
     */
    public NodeConstant(Type type, RoboValue value) {
        this.value = value;
        this.type = type;
    }

    public RoboValue getValue() {
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

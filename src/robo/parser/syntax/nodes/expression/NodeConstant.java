package robo.parser.syntax.nodes.expression;

import robo.parser.execution.values.RoboValue;
import robo.parser.lexical.Type;
import robo.parser.execution.visitor.expression.ExpressionNodeVisitor;

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

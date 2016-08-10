package robo.parser.syntax.nodes.value;

import robo.parser.lexical.Type;

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

}

package robo.parser.syntax.nodes.statements;


import robo.parser.lexical.Token;
import robo.parser.syntax.nodes.Node;
import robo.parser.syntax.nodes.value.NodeExpression;

/**
 * Created by gregor on 09.08.16..
 */
public class AsgnValStatement extends Node {

    // TODO RIJESITI SE TOKENA
    private Token var;


    private NodeExpression expression;


    public AsgnValStatement(Token var, NodeExpression expression) {
        super();
        this.var = var;
        this.expression = expression;
    }


    public NodeExpression getExpression() {
        return expression;
    }

}

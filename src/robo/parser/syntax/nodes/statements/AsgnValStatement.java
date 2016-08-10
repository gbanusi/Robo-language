package robo.parser.syntax.nodes.statements;


import robo.parser.syntax.nodes.Node;
import robo.parser.syntax.nodes.value.NodeExpression;

/**
 * Created by gregor on 09.08.16..
 */
public class AsgnValStatement extends Node {


    private String varName;


    private NodeExpression expression;


    public AsgnValStatement(String varName, NodeExpression expression) {
        super();
        this.varName = varName;
        this.expression = expression;
    }


    public String getVarName() {
        return varName;
    }


    public NodeExpression getExpression() {
        return expression;
    }

}

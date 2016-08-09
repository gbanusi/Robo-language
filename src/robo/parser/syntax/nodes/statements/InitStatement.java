package robo.parser.syntax.nodes.statements;

import com.sun.org.apache.xpath.internal.ExpressionNode;

/**
 * Created by gregor on 09.08.16..
 */
public class InitStatement {


    private String varName;


    private ExpressionNode expression;


    public InitStatement(String varName, ExpressionNode expression) {
        super();
        this.varName = varName;
        this.expression = expression;
    }


    public String getVarName() {
        return varName;
    }


    public ExpressionNode getExpression() {
        return expression;
    }

}

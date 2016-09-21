package parser.syntax.nodes.statements.assignation;


import parser.execution.visitor.statement.NodeVisitor;
import parser.lexical.tokenizer.TokenType;
import parser.syntax.SyntaxException;
import parser.syntax.nodes.Node;
import parser.syntax.nodes.expression.NodeExpression;
import parser.syntax.parser.ParserHelper;

/**
 * Created by gregor on 09.08.16..
 */
public class AssignVarStatement extends Node {

    private String var;

    private NodeExpression expression;

    public AssignVarStatement(String var, NodeExpression expression) {
        super();
        this.var = var;
        this.expression = expression;
    }

    public String getVar() {
        return var;
    }

    public NodeExpression getExpression() {
        return expression;
    }

    public static Node parseAssignVariable(String name, ParserHelper parserHelper) {
        if (! parserHelper.match(TokenType.ASSIGN)) {
            throw new SyntaxException("Assign was expected." + parserHelper.peek().getTokenType());
        }
        NodeExpression exp = parserHelper.parseValue();
        if (! parserHelper.match(TokenType.SEMICOLON)) {
            throw new SyntaxException("Semicolon was expected.");
        }
        return new AssignVarStatement(name, exp);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}

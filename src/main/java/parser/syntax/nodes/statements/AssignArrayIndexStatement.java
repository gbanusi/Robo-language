package parser.syntax.nodes.statements;


import parser.execution.visitor.statement.NodeVisitor;
import parser.lexical.TokenType;
import parser.syntax.SyntaxException;
import parser.syntax.nodes.Node;
import parser.syntax.nodes.expression.NodeExpression;
import parser.syntax.parser.ParserHelper;

import java.util.List;

/**
 * Created by gregor on 09.08.16..
 */
public class AssignArrayIndexStatement extends Node {

    private String var;

    private List<NodeExpression> indexes;

    private NodeExpression expression;

    public AssignArrayIndexStatement(String var, List<NodeExpression> indexes, NodeExpression expression) {
        super();
        this.var = var;
        this.indexes = indexes;
        this.expression = expression;
    }

    public String getVar() {
        return var;
    }

    public List<NodeExpression> getIndexes() {
        return indexes;
    }

    public NodeExpression getExpression() {
        return expression;
    }

    public static Node parseAssignArrayIndex(String name, ParserHelper parserHelper) {
        parserHelper.pop();
        List<NodeExpression> values = parserHelper.parseExpressionsArray(TokenType.SEMICOLON);
        if (! parserHelper.match(TokenType.CLOSED_SQUARE)) {
            throw new SyntaxException("Closed square brackets expected, instead of: '" + parserHelper.peek().getTokenType() + "'!");
        }
        if (! parserHelper.match(TokenType.ASSIGN)) {
            throw new SyntaxException("Assign was expected.");
        }
        NodeExpression exp = parserHelper.parseValue();
        if (! parserHelper.match(TokenType.SEMICOLON)) {
            throw new SyntaxException("Semicolon was expected.");
        }
        return new AssignArrayIndexStatement(name, values, exp);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}

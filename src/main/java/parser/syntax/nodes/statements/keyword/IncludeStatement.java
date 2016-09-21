package parser.syntax.nodes.statements.keyword;

import parser.execution.visitor.statement.NodeVisitor;
import parser.lexical.tokenizer.TokenType;
import parser.syntax.nodes.Node;
import parser.syntax.nodes.expression.NodeExpression;
import parser.syntax.parser.ParserHelper;

/**
 * Created by gregor on 14.08.16..
 * <p>
 * AKA Asign function
 */
public class IncludeStatement extends Node {

    private NodeExpression value;

    public IncludeStatement(NodeExpression value) {
        this.value = value;
    }

    public NodeExpression getValue() {
        return value;
    }

    public static Node parseInclude(ParserHelper parserHelper) {
        parserHelper.match(TokenType.INCLUDE);
        NodeExpression val = parserHelper.parseValue();
        parserHelper.match(TokenType.SEMICOLON);
        return new IncludeStatement(val);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}

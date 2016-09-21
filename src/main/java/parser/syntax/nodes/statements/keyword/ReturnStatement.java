package parser.syntax.nodes.statements.keyword;

import parser.execution.visitor.statement.NodeVisitor;
import parser.lexical.tokenizer.TokenType;
import parser.syntax.nodes.Node;
import parser.syntax.nodes.expression.NodeExpression;
import parser.syntax.parser.ParserHelper;

/**
 * Created by gregor on 14.08.16..
 *
 * AKA Asign function
 */
public class ReturnStatement extends Node{

    private NodeExpression value;

    public ReturnStatement(NodeExpression value) {
        this.value = value;
    }

    public NodeExpression getValue() {
        return value;
    }

    public static Node parseReturn(ParserHelper parserHelper) {
        parserHelper.match(TokenType.RETURN);
        NodeExpression val = parserHelper.parseValue();
        parserHelper.match(TokenType.SEMICOLON);
        return new ReturnStatement(val);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}

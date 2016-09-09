package parser.syntax.nodes.statements;

import parser.execution.visitor.statement.NodeVisitor;
import parser.lexical.TokenType;
import parser.syntax.SyntaxException;
import parser.syntax.nodes.Node;
import parser.syntax.parser.ParserHelper;

/**
 * Created by gregor on 13.08.16..
 */
public class ContinueStatement extends Node {

    public static Node parseContinue(ParserHelper parserHelper) {
        parserHelper.match(TokenType.CONTINUE);
        if (! parserHelper.match(TokenType.SEMICOLON)) {
            throw new SyntaxException("Declaration type missing...");
        }
        return new ContinueStatement();
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}

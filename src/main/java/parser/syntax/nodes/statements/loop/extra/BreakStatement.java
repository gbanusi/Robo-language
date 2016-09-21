package parser.syntax.nodes.statements.loop.extra;

import parser.execution.visitor.statement.NodeVisitor;
import parser.lexical.tokenizer.TokenType;
import parser.syntax.SyntaxException;
import parser.syntax.nodes.Node;
import parser.syntax.parser.ParserHelper;

/**
 * Created by gregor on 13.08.16..
 */
public class BreakStatement extends Node {

    public static Node parseBreak(ParserHelper parserHelper) {
        parserHelper.match(TokenType.BREAK);
        if (! parserHelper.match(TokenType.SEMICOLON)) {
            throw new SyntaxException("Declaration type missing...");
        }
        return new BreakStatement();
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}

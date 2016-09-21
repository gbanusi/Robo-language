package parser.syntax.nodes.statements.keyword;

import parser.execution.values.RoboBool;
import parser.execution.visitor.statement.NodeVisitor;
import parser.lexical.tokenizer.TokenType;
import parser.lexical.type.Type;
import parser.syntax.nodes.Node;
import parser.syntax.nodes.expression.value.right.NodeConstant;
import parser.syntax.nodes.expression.NodeExpression;
import parser.syntax.parser.Parser;
import parser.syntax.parser.ParserHelper;

import java.util.ArrayList;
import java.util.List;

public class IfBlockStatement extends Node {

    private List<Node> ifStatements = new ArrayList<>();

    public IfBlockStatement(List<Node> statements) {
        super();
        this.ifStatements=statements;
    }


    public List<Node> getIfStatements() {
        return ifStatements;
    }

    public static Node parseIfBlock(ParserHelper parserHelper, Parser parser) {
        List<Node> ifStatements = new ArrayList<>();
        boolean hasElse;
        do {
            parserHelper.pop();
            hasElse = false;
            List<Node> statements;
            parserHelper.match(TokenType.OPEN_PARENTHESES);
            NodeExpression cond = parserHelper.parseValue();
            parserHelper.match(TokenType.CLOSED_PARENTHESES);
            parserHelper.match(TokenType.OPEN_CURLY);
            statements = parser.parse();
            ifStatements.add(new IfStatement(statements, cond));
            if (parserHelper.peek().getTokenType() == TokenType.ELSE) {
                parserHelper.pop();
                hasElse = true;
            }
        } while (parserHelper.peek().getTokenType() == TokenType.IF);

        if (hasElse) {
            parserHelper.match(TokenType.OPEN_CURLY);
            List<Node> statements = parser.parse();
            ifStatements.add(new IfStatement(statements, new NodeConstant(Type.Bool, new RoboBool(true))));
        }
        return new IfBlockStatement(ifStatements);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}

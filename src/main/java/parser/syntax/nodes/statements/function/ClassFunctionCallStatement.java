package parser.syntax.nodes.statements.function;

import parser.execution.visitor.statement.NodeVisitor;
import parser.lexical.tokenizer.TokenType;
import parser.syntax.nodes.Node;
import parser.syntax.nodes.expression.NodeExpression;
import parser.syntax.nodes.expression.value.left.NodeVariable;
import parser.syntax.parser.ParserHelper;

/**
 * Created by gregor on 02.10.16..
 */
public class ClassFunctionCallStatement extends Node {

    private NodeExpression dot;

    public ClassFunctionCallStatement(NodeExpression dot) {
        super();
        this.dot = dot;
    }

    public NodeExpression getExpression() {
        return dot;
    }


    public static Node parseClassFunctionCall(ParserHelper parserHelper, String name) {
        NodeExpression exp = parserHelper.parseDot(new NodeVariable(name));
        parserHelper.match(TokenType.SEMICOLON);
        return new ClassFunctionCallStatement(exp);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}


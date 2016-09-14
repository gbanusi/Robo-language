package parser.syntax.nodes.statements;

import parser.execution.visitor.statement.NodeVisitor;
import parser.lexical.TokenType;
import parser.lexical.Type;
import parser.syntax.SyntaxException;
import parser.syntax.nodes.Node;
import parser.syntax.nodes.expression.NodeExpression;
import parser.syntax.parser.ParserHelper;

import java.util.ArrayList;
import java.util.List;


public class DefVarStatement extends Node {

    private List<String> variables;

    private Type type;

    private boolean isConst;

    private List<Node> assigns;

    public DefVarStatement(List<String> nv, Type type, boolean isConst, List<Node> assigns) {
        this.variables = new ArrayList<>(nv);
        this.type = type;
        this.isConst = isConst;
        this.assigns = assigns;
    }

    public Type getType() {
        return type;
    }

    public List<String> getVariables() {
        return new ArrayList<>(variables);
    }

    public boolean isConst() {
        return isConst;
    }

    public List<Node> getAssigns() {
        return assigns;
    }

    public static Node parseDef(ParserHelper parserHelper) {
        if (parserHelper.peek().getTokenType() != TokenType.BASIC) {
            throw new SyntaxException("Declaration type missing...");
        }
        Type type = (Type) parserHelper.pop().getValue();

        boolean isArray = false;
        DefArrayType typeDef = null;
        List<NodeExpression> matrixDim;
        if (parserHelper.match(TokenType.OPEN_SQUARE)) {
            isArray = true;
            matrixDim = parserHelper.parseExpressionsArray(TokenType.SEMICOLON);
            if(matrixDim.size() > 2){
                throw new SyntaxException("Only 2-dim matrix supported!");
            }
            if (!parserHelper.match(TokenType.CLOSED_SQUARE)) {
                throw new SyntaxException("Expected ']', got: '" + parserHelper.peek().getValue() + "', type: '" + parserHelper.peek().getTokenType() + "'.");
            }
            typeDef = new DefArrayType(type, matrixDim);
        }


        boolean isConst = false;
        if (parserHelper.match(TokenType.CONST)) {
            isConst = true;
        }
        List<String> variables = new ArrayList<>();
        List<Node> assigns = new ArrayList<>();
        while (true) {
            if (parserHelper.peek().getTokenType() != TokenType.IDENT) {
                throw new SyntaxException("Identifier was expected, got: '" + parserHelper.peek().getValue() + "', type of: '" + parserHelper.peek().getTokenType() + ".");
            }
            variables.add((String) parserHelper.pop().getValue());
            if (parserHelper.match(TokenType.ASSIGN)) {
                assigns.add(new AssignVarStatement(variables.get(variables.size() - 1), parserHelper.parseValue()));
            }
            if (parserHelper.match(TokenType.COMMA)) {
                continue;
            }
            break;
        }
        if (!parserHelper.match(TokenType.SEMICOLON)) {
            throw new SyntaxException("Semicolon was expected instead of: '" + parserHelper.peek().getTokenType() + "'.");
        }
        if(isArray){
            return new DefArrayStatement(variables, isConst, assigns, typeDef);
        } else {
            return new DefVarStatement(variables, type, isConst, assigns);
        }
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}
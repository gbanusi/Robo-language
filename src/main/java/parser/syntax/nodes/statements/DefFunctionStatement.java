package parser.syntax.nodes.statements;

import parser.execution.visitor.statement.NodeVisitor;
import parser.lexical.TokenType;
import parser.lexical.Type;
import parser.syntax.SyntaxException;
import parser.syntax.nodes.Node;
import parser.syntax.nodes.expression.NodeExpression;
import parser.syntax.parser.Parser;
import parser.syntax.parser.ParserHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gregor on 13.08.16..
 */
public class DefFunctionStatement extends Node {


    private List<Node> statements;

    private List<Node> parameters;

    private Type returnType;

    private String fName;

    public DefFunctionStatement(List<Node> statements, List<Node> parameters, Type returnType, String fName) {
        this.statements = statements;
        this.parameters = parameters;
        this.returnType = returnType;
        this.fName = fName;
    }

    public List<Node> getStatements() {
        return new LinkedList<>(statements);
    }

    public List<Node> getParameters() {
        return new LinkedList<>(parameters);
    }

    public Type getReturnType() {
        return returnType;
    }

    public String getfName() {
        return fName;
    }

    public static Node parseFunction(ParserHelper parserHelper, Parser parser) {
        parserHelper.match(TokenType.FUNCTION);

        if (parserHelper.peek().getTokenType() != TokenType.BASIC) {
            throw new SyntaxException("Declaration type missing...");
        }

        Type type = (Type) parserHelper.pop().getValue();
        if (parserHelper.peek().getTokenType() != TokenType.IDENT) {
            throw new SyntaxException("Declaration type missing...");
        }

        String fName = (String) parserHelper.pop().getValue();


        LinkedList<Node> parameters = new LinkedList<>();
        parserHelper.match(TokenType.OPEN_PARENTHESES);
        if (parserHelper.peek().getTokenType() != TokenType.CLOSED_PARENTHESES) {
            while (parserHelper.peek().getTokenType() == TokenType.BASIC) {

                List<String> paramNames = new ArrayList<>();
                Type varType = (Type) parserHelper.pop().getValue();

                boolean isArray = false;
                DefArrayType typeDef = null;
                List<NodeExpression> matrixDim;
                // TODO-1 general array?
                if (parserHelper.match(TokenType.OPEN_SQUARE)) {
                    isArray = true;
                    if (!parserHelper.match(TokenType.CLOSED_SQUARE)) {
                        throw new SyntaxException("Expected ']', got: '" + parserHelper.peek().getValue() + "', type: '" + parserHelper.peek().getTokenType() + "'.");
                    }
                    typeDef = new DefArrayType(varType, new ArrayList<>());
                }

                boolean isConst = false;
                if (parserHelper.match(TokenType.CONST)) {
                    isConst = true;
                }

                if (parserHelper.peek().getTokenType() != TokenType.IDENT) {
                    throw new SyntaxException("Identifier was expected.");
                }
                paramNames.add((String) parserHelper.pop().getValue());

                //TODO-5 UBACITI DEFAULTNE VRIJEDNOSTI?

                if (isArray) {
                    parameters.add(new DefArrayStatement(paramNames, isConst, new ArrayList<Node>(), typeDef));
                } else {
                    parameters.add(new DefVarStatement(paramNames, varType, isConst, new ArrayList<Node>()));
                }
                if (parserHelper.match(TokenType.COMMA)) {
                    continue;
                }
                break;
            }
        }

        parserHelper.match(TokenType.CLOSED_PARENTHESES);
        parserHelper.match(TokenType.OPEN_CURLY);
        List<Node> statements = parser.parse();

        return new DefFunctionStatement(statements, parameters, type, fName);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}

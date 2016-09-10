package parser.syntax.parser;

import parser.execution.values.RoboBool;
import parser.execution.values.RoboDouble;
import parser.execution.values.RoboInteger;
import parser.execution.values.RoboString;
import parser.lexical.*;
import parser.syntax.SyntaxException;
import parser.syntax.nodes.expression.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregor on 27.08.16..
 */
public class ParserHelper {

    private Tokenizer tokenizer;

    public ParserHelper(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public Token peek() {
        return tokenizer.getCurrentToken();
    }

    public Token pop() {
        Token t = tokenizer.getCurrentToken();
        tokenizer.nextToken();
        return t;
    }

    public boolean match(TokenType t) {
        if (peek().getTokenType() == t) {
            if (TokenType.EOF != t) {
                tokenizer.nextToken();
            }
            return true;
        }
        return false;
    }

    public NodeExpression parseValue() {
        NodeExpression x = parseJoin();
        while (match(TokenType.OR)) {
            x = new NodeExpressionOr(x, parseJoin());
        }
        return x;
    }

    private NodeExpression parseJoin() {
        NodeExpression x = parseEquality();
        while (match(TokenType.AND)) {
            x = new NodeExpressionAnd(x, parseEquality());
        }
        return x;
    }

    private NodeExpression parseEquality() {
        NodeExpression x = parseRelation();
        while (peek().getTokenType().equals(TokenType.EQ)
                || peek().getTokenType().equals(TokenType.NE)) {

            if (match(TokenType.EQ)) {
                x = new NodeExpressionEquality(x, parseRelation());
            } else {
                match(TokenType.NE);
                x = new NodeExpressionNoEquality(x, parseRelation());
            }
        }
        return x;
    }

    private NodeExpression parseRelation() {
        NodeExpression x = parsePlusMinusOp();
        switch (peek().getTokenType()) {
            case LT:
                pop();
                return new NodeExpressionLTRelation(x, parsePlusMinusOp());
            case LE:
                pop();
                return new NodeExpressionLERelation(x, parsePlusMinusOp());
            case GE:
                pop();
                return new NodeExpressionGERelation(x, parsePlusMinusOp());
            case GT:
                pop();
                return new NodeExpressionGTRelation(x, parsePlusMinusOp());
            default:
                return x;
        }
    }

    private NodeExpression parsePlusMinusOp() {
        NodeExpression x = parseMultDivOp();
        while (peek().getTokenType().equals(TokenType.OP_PLUS)
                || peek().getTokenType().equals(TokenType.OP_MINUS)) {

            if (match(TokenType.OP_PLUS)) {
                x = new NodeExpressionAdd(x, parseMultDivOp());
            } else {
                match(TokenType.OP_MINUS);
                x = new NodeExpressionSub(x, parseMultDivOp());
            }
        }
        return x;
    }

    private NodeExpression parseMultDivOp() {
        NodeExpression x = parseUnary();
        while (peek().getTokenType().equals(TokenType.OP_MULT)
                || peek().getTokenType().equals(TokenType.OP_DIV)) {

            if (match(TokenType.OP_MULT)) {
                x = new NodeExpressionMult(x, parseUnary());
            } else {
                match(TokenType.OP_DIV);
                x = new NodeExpressionDiv(x, parseUnary());
            }
        }
        return x;
    }

    private NodeExpression parseUnary() {
        if (match(TokenType.UN_MINUS)) {
            return new NodeExpressionUnMinus(factor());
        } else if (match(TokenType.UN_REFERENCE)) {
            return new NodeExpressionUnReference(parseIdentFuncArray(true));
        } else if (match(TokenType.NOT)) {
            return new NodeExpNot(factor());
        } else {
            return factor();
        }
    }

    private NodeExpression factor() {
        NodeExpression x = null;
        switch (peek().getTokenType()) {
            case OPEN_PARENTHESES:
                pop();
                x = parseValue();
                if (!match(TokenType.CLOSED_PARENTHESES)) {
                    throw new SyntaxException("Closed parentheses expected!");
                }
                return x;
            case OPEN_SQUARE:
                pop();
                readingMatrix = true;
                List<NodeExpression> values = parseExpressionsArray(TokenType.COMMA);
                if (!match(TokenType.CLOSED_SQUARE)) {
                    throw new SyntaxException("Closed square brackets expected instead of '" + peek().getTokenType() + "'!");
                }

                if(maxDepth <= 1) {
                    Integer rows = values.size();
                    Integer cols = maxDepth;
                    Type type = new TypeMatrix(Type.Unknown, rows, cols);
                    return new NodeArray(type, values);
                } else {
                    Integer length = values.size();
                    Type type = new TypeArray(Type.Unknown, length);
                    return new NodeMatrix(type, values);
                }

            case CONSTANT:
                x = parseConstant();
                return x;
            case TRUE:
                pop();
                x = new NodeConstant(Type.Bool, new RoboBool(true));
                return x;
            case FALSE:
                pop();
                x = new NodeConstant(Type.Bool, new RoboBool(false));
                return x;
            case IDENT:
                return parseIdentFuncArray(false);
            default:
                throw new SyntaxException("Expression not recognized '" + peek().getTokenType() + "' ...");
        }
    }

    private NodeExpression parseIdentFuncArray(boolean isVariable) {
        String name = (String) pop().getValue();
        if (match(TokenType.OPEN_PARENTHESES)) {
            if (isVariable) {
                throw new SyntaxException("Functions cannot have reference operator!");
            }
            return parseFuncCall(name);
        } else if (match(TokenType.OPEN_SQUARE)) {
            List<NodeExpression> vars;
            vars = parseExpressionsArray(TokenType.COMMA);
            if (!match(TokenType.CLOSED_SQUARE)) {
                throw new SyntaxException("Closed square bracket expected instead of: '" + peek().getTokenType() + "'!");
            }
            return new NodeArrayIndexing(name, vars);
        }
        return new NodeVariable(name);
    }


    public NodeExpression parseFuncCall(String name) {
        List<NodeExpression> vars = new ArrayList<>();
        if (!match(TokenType.CLOSED_PARENTHESES)) {
            vars = parseExpressionsArray(TokenType.COMMA);
        }
        match(TokenType.CLOSED_PARENTHESES);
        return new NodeFunction(vars, name);
    }

    private boolean readingMatrix = false;

    private int arrayLength = -1;

    private int maxDepth = 0;

    private int arrayDepth = 0;

    public List<NodeExpression> parseExpressionsArray(TokenType separateBy) {

        if(readingMatrix){
            arrayDepth++;
            if(maxDepth < arrayDepth){
                maxDepth = arrayDepth;
            }
            if(arrayDepth > 2){
                throw new SyntaxException("Only 2-dim arrays supported...");
            }
        }

        List<NodeExpression> list = new ArrayList<>();
        while (true) {
            list.add(parseValue());
            if (!match(separateBy)) {
                break;
            }
        }

        if(readingMatrix) {
            // we are positioned in a row and don't know it's size
            if (arrayDepth == 2 && arrayLength == -1) {
                arrayLength = list.size();
            } else if(arrayDepth == 2){
                // compare the first computed size with others
                if (arrayLength != list.size()) {
                    throw new SyntaxException("Matrix not properly defined, dimensions incorrect!");
                }
            }
            arrayDepth--;
            if(arrayDepth == 0){
                readingMatrix = false;
                arrayLength = -1;
                maxDepth = 0;
            }
        }

        return list;
    }

    private NodeExpression parseConstant() {
        if (peek().getValue() instanceof Integer) {
            return new NodeConstant(Type.Int, new RoboInteger((Integer) pop().getValue()));
        } else if (peek().getValue() instanceof Double) {
            return new NodeConstant(Type.Double, new RoboDouble((Double) pop().getValue()));
        } else if (peek().getValue() instanceof String) {
            return new NodeConstant(Type.String, new RoboString((String) pop().getValue()));
        } else {
            throw new SyntaxException("Syntax error, cannot get type of: '" + peek().getValue() + "', type of: " + peek().getTokenType());
        }
    }

}

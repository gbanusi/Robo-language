package robo.parser.syntax;

import robo.parser.Vector;
import robo.parser.lexical.Token;
import robo.parser.lexical.TokenType;
import robo.parser.lexical.Tokenizer;
import robo.parser.lexical.Type;
import robo.parser.syntax.nodes.Node;
import robo.parser.syntax.nodes.ProgramNode;
import robo.parser.syntax.nodes.statements.AsgnValStatement;
import robo.parser.syntax.nodes.statements.DefStatement;
import robo.parser.syntax.nodes.statements.PrintStatement;
import robo.parser.syntax.nodes.statements.VarEnvironment;
import robo.parser.syntax.nodes.value.NodeConstant;
import robo.parser.syntax.nodes.value.NodeExpression;
import robo.parser.syntax.nodes.value.NodeVariable;
import robo.parser.syntax.nodes.value.expression.*;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private VarEnvironment env;

    private Tokenizer tokenizer;

    private ProgramNode programNode;

    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
        programNode = parse();
    }

    public ProgramNode getProgramNode() {
        return programNode;
    }

    public boolean match(TokenType t) {
        if (peek().getTokenType() == t) {
            tokenizer.nextToken();
            return true;
        }
        return false;
    }

    public Token peek() {
        return tokenizer.getCurrentToken();
    }

    public Token pop() {
        Token t = tokenizer.getCurrentToken();
        tokenizer.nextToken();
        return t;
    }


    private ProgramNode parse() {
        List<Node> statements = new ArrayList<>();
        env = new VarEnvironment();

        while (true) {
            // Ako je kraj programa, gotovi smo:
            if (match(TokenType.EOF)) {
                break;
            }

            // Prema sintaksi može ići varijabla
            if (peek().getTokenType() == TokenType.IDENT) {
                statements.add(parseAsgnVal());
                continue;
            }

            // inicijalizacija varijable
            if (peek().getTokenType() == TokenType.BASIC) {
                statements.add(parseDef());
                continue;
            }

            // ili ključna riječ
            if (!peek().getTokenType().getCode().equals("KEYWORD")) {
                throw new SyntaxException("Keyword expected!");
            } else {
                parseKeyword();
            }
        }
        // Obradili smo čitav program:
        return new ProgramNode(statements);
    }

    private void parseKeyword() {

        switch (peek().getTokenType()) {
            case PRINT:
                parsePrint();
                break;
            case IF:
                parseIf();
                break;
//            case BREAK:
//                parsePrint();
//                break;
//            case DO:
//                parsePrint();
//                break;
//            case ELSE:
//                parsePrint();
//                break;
//            case WHILE:
//                parsePrint();
//                break;
//            case TokenType.FUNCTION:
//                parseFunction();
//                break;
        }
    }


    private Node parseDef() {
        Type type = (Type) pop().getValue();
        List<String> variables = new ArrayList<>();
        while (true) {
            if (peek().getTokenType() != TokenType.IDENT) {
                throw new SyntaxException("Identifier was expected.");
            }
            variables.add((String) pop().getValue());
            if (match(TokenType.COMMA)) {
                continue;
            }
            break;
        }
        tokenizer.nextToken();
        match(TokenType.SEMICOLON);
        return new DefStatement(variables, type);
    }


    private Node parseAsgnVal() {
        String name = (String) pop().getValue();
        match(TokenType.ASSIGN);
        NodeExpression exp = parseValue();
        match(TokenType.SEMICOLON);
        return new AsgnValStatement(name, exp);
    }

    private NodeExpression parseValue() {
        NodeExpression x = parseJoin();
        while (peek().getTokenType().equals(TokenType.OR)) {
            pop();
            x = new NodeExpressionOr(x, parseJoin());
        }
        return x;
    }

    private NodeExpression parseJoin() {
        NodeExpression x = parseEquality();
        while (peek().getTokenType().equals(TokenType.AND)) {
            pop();
            x = new NodeExpressionAnd(x, parseEquality());
        }
        return x;
    }

    private NodeExpression parseEquality() {
        NodeExpression x = parseRelation();
        while (peek().getTokenType().equals(TokenType.EQ)
                || peek().getTokenType().equals(TokenType.NE)) {

            if (peek().getTokenType().equals(TokenType.EQ)) {
                pop();
                x = new NodeExpressionEquality(x, parseRelation());
            } else {
                pop();
                x = new NodeExpressionNotEquality(x, parseRelation());
            }
        }
        return x;
    }

    private NodeExpression parseRelation() {
        NodeExpression x = parsePlusMinusOp();
        switch (peek().getTokenType()) {
            case LT:
                pop();
                return new NodeExpressionRelation(x, parsePlusMinusOp(), TokenType.LT);
            case LE:
                pop();
                return new NodeExpressionRelation(x, parsePlusMinusOp(), TokenType.LE);
            case GE:
                pop();
                return new NodeExpressionRelation(x, parsePlusMinusOp(), TokenType.GE);
            case GT:
                pop();
                return new NodeExpressionRelation(x, parsePlusMinusOp(), TokenType.GT);
            default:
                return x;
        }
    }

    private NodeExpression parsePlusMinusOp() {
        NodeExpression x = parseMultDivOp();
        while (peek().getTokenType().equals(TokenType.OP_PLUS)
                || peek().getTokenType().equals(TokenType.OP_MINUS)) {

            if (peek().getTokenType().equals(TokenType.OP_PLUS)) {
                pop();
                x = new NodeExpressionAdd(x, parseMultDivOp());
            } else {
                pop();
                x = new NodeExpressionSub(x, parseMultDivOp());
            }
        }
        return x;
    }

    private NodeExpression parseMultDivOp() {
        NodeExpression x = parseUnary();
        while (peek().getTokenType().equals(TokenType.OP_MULT)
                || peek().getTokenType().equals(TokenType.OP_DIV)) {

            if (peek().getTokenType().equals(TokenType.OP_MULT)) {
                pop();
                x = new NodeExpressionAdd(x, parseUnary());
            } else {
                pop();
                x = new NodeExpressionSub(x, parseUnary());
            }
            pop();
        }
        return x;
    }

    //  TODO NOT (!) TO ADD
    private NodeExpression parseUnary() {
        if (peek().getTokenType().equals(TokenType.UN_MINUS)) {
            pop();
            return new NodeExpressionUnMinus(parseUnary());
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
                match(TokenType.CLOSED_PARENTHESES);
                return x;
            case CONSTANT:
                x = createConstant();
                return x;
//            TODO
//            case .TRUE:
//                x = .True;
//                move();
//                return x;
//            case .FALSE:
//                x = .False;
//                move();
//                return x;
            case IDENT:
                String name = (String) pop().getValue();
                return new NodeVariable(name);
            default:
                return x;
        }
    }

    private NodeExpression createConstant() {
        if(peek().getValue() instanceof Integer){
            return new NodeConstant(Type.Int, pop().getValue());
        } else if(peek().getValue() instanceof Double){
            return new NodeConstant(Type.Double, pop().getValue());
        }
//        TODO
//        else if(peek().getValue() instanceof Char){
//            return new NodeConstant(Type.Char, pop().getValue());
//        } else if(peek().getValue() instanceof Double){
//            return new NodeConstant(Type.Double, pop().getValue());
//        }
        else {
            throw new SyntaxException("syntax error");
        }
    }


    private NodeExpression parseAtomicValue() {
        if (peek().getTokenType() == TokenType.IDENT) {
            String varName = (String) peek().getValue();
            tokenizer.nextToken();
//			return new NodeVariable(varName); TODO
        }
        if (peek().getTokenType() == TokenType.VECTOR_CONSTANT) {
            Vector vector = (Vector) peek().getValue();
            tokenizer.nextToken();
//            return new NodeVector(vector); TODO
        }
        if (peek().getTokenType() == TokenType.OPEN_PARENTHESES) {
            tokenizer.nextToken();
            NodeExpression expression = parseValue();
            if (peek().getTokenType() != TokenType.CLOSED_PARENTHESES) {
                throw new SyntaxException("Closed parentheses was expected.");
            }
            tokenizer.nextToken();
            return expression;
        }
        throw new SyntaxException("Unexpeced peek value.");
    }


    private Node parsePrint() {
        List<NodeExpression> list = new ArrayList<>();
        list.add(parseValue());
        while (true) {
            if (peek().getTokenType() != TokenType.COMMA) {
                break;
            }
            tokenizer.nextToken();
            list.add(parseValue());
        }
        if (peek().getTokenType() != TokenType.SEMICOLON) {
            throw new SyntaxException("Semicolon was expected.");
        }
        tokenizer.nextToken();
        return new PrintStatement(list);
    }
}

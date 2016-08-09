package robo.parser.syntax;

import robo.parser.Vector;
import robo.parser.lexical.Token;
import robo.parser.lexical.TokenType;
import robo.parser.lexical.Tokenizer;
import robo.parser.syntax.nodes.Node;
import robo.parser.syntax.nodes.ProgramNode;
import robo.parser.syntax.nodes.operations.NodeExpression;
import robo.parser.syntax.nodes.operations.NodeExpressionAdd;
import robo.parser.syntax.nodes.operations.NodeExpressionSub;
import robo.parser.syntax.nodes.statements.IfStatement;
import robo.parser.syntax.nodes.statements.PrintStatement;
import robo.parser.syntax.nodes.statements.VarEnvironment;

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

    public boolean match(TokenType t){
        if(peek().getTokenType() == t){
            tokenizer.nextToken();
            return true;
        }
        return false;
    }

    public Token peek(){
        return tokenizer.getCurrentToken();
    }

    public Token pop(){
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
            if (peek().getTokenType() == TokenType.IDENT ) {
                // ako je već deklarirana
                if(VarEnvironment.get(peek()) == null) {
                    throw new SyntaxException("Variable not declared!");
                } else {
                    statements.add(parseAsgnVal());
                    continue;
                }
            }

            // ili ključna riječ
            if(! peek().getTokenType().getCode().equals("KEYWORD")){
                throw new SyntaxException("Keyword expected!");
            }

            parseKeyword();
        }
        // Obradili smo čitav program:
        return new ProgramNode(statements);
    }

    private void parseKeyword() {

        switch (peek().getTokenType()){
            case TokenType.
        }
    }





    private Node parseDef() {
        List<String> variables = new ArrayList<>();
        while (true) {
            if (peek().getTokenType() != TokenType.IDENT) {
                throw new SyntaxException("Identifier was expected.");
            }
            variables.add((String) peek().getValue());
            tokenizer.nextToken();
            if (peek().getTokenType() == TokenType.COMMA) {
                tokenizer.nextToken();
                continue;
            }
            break;
        }
        tokenizer.nextToken();
        if (peek().getTokenType() != TokenType.KEYWORD) {
            throw new SyntaxException("A keyword was expected.");
        }
        String varType = (String) peek().getValue();
        if (!"vector".equals(varType)) {
            throw new SyntaxException("Keyword 'vector' was expected.");
        }
        tokenizer.nextToken();
        if (peek().getTokenType() != TokenType.SEMICOLON) {
            throw new SyntaxException("A semicolon was expected.");
        }
        tokenizer.nextToken();
//		return new DefStatement(variables, varType); TODO
        return null;
    }


    private Node parseLet() {
        if (peek().getTokenType() != TokenType.IDENT) {
            throw new SyntaxException("Identifier was expected.");
        }
        String varName = (String) peek().getValue();
        tokenizer.nextToken();
        if (peek().getTokenType() != TokenType.ASSIGN) {
            throw new SyntaxException("Assignment was expected.");
        }
        tokenizer.nextToken();
        NodeExpression expr = parseExpression();
        if (peek().getTokenType() != TokenType.SEMICOLON) {
            throw new SyntaxException("Semicolon was expected.");
        }
        tokenizer.nextToken();
        return new IfStatement(varName, expr);
    }


    private NodeExpression parseExpression() {
        NodeExpression first = parseAtomicValue();
        while (true) {
            if (peek().getTokenType() == TokenType.OP_PLUS) {
                tokenizer.nextToken();
                NodeExpression second = parseAtomicValue();
                first = new NodeExpressionAdd(first, second);
                continue;
            }
            if (peek().getTokenType() == TokenType.OP_MINUS) {
                tokenizer.nextToken();
                NodeExpression second = parseAtomicValue();
                first = new NodeExpressionSub(first, second);
                continue;
            }
            break;
        }
        return first;
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
            NodeExpression expression = parseExpression();
            if (peek().getTokenType() != TokenType.CLOSED_PARENTHESES) {
                throw new SyntaxException("Closed parentheses was expected.");
            }
            tokenizer.nextToken();
            return expression;
        }
        throw new SyntaxException("Unexpeced peek type.");
    }


    private Node parsePrint() {
        List<NodeExpression> list = new ArrayList<>();
        list.add(parseExpression());
        while (true) {
            if (peek().getTokenType() != TokenType.COMMA) {
                break;
            }
            tokenizer.nextToken();
            list.add(parseExpression());
        }
        if (peek().getTokenType() != TokenType.SEMICOLON) {
            throw new SyntaxException("Semicolon was expected.");
        }
        tokenizer.nextToken();
        return new PrintStatement(list);
    }
}

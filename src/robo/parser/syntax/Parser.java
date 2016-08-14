package robo.parser.syntax;

import robo.parser.Vector;
import robo.parser.lexical.Token;
import robo.parser.lexical.TokenType;
import robo.parser.lexical.Tokenizer;
import robo.parser.lexical.Type;
import robo.parser.syntax.nodes.Node;
import robo.parser.syntax.nodes.ProgramNode;
import robo.parser.syntax.nodes.statements.*;
import robo.parser.syntax.nodes.value.NodeConstant;
import robo.parser.syntax.nodes.value.NodeExpression;
import robo.parser.syntax.nodes.value.NodeFunction;
import robo.parser.syntax.nodes.value.NodeVariable;
import robo.parser.syntax.nodes.value.expression.*;

import java.util.ArrayList;
import java.util.List;

public class Parser {


    private Tokenizer tokenizer;

    private ProgramNode programNode;

    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
        programNode = new ProgramNode(parse());
    }

    public ProgramNode getProgramNode() {
        return programNode;
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

    public Token peek() {
        return tokenizer.getCurrentToken();
    }

    public Token pop() {
        Token t = tokenizer.getCurrentToken();
        tokenizer.nextToken();
        return t;
    }


    private List<Node> parse() {
        List<Node> statements = new ArrayList<>();

        while (true) {
            // Ako je kraj programa, gotovi smo:
            if (match(TokenType.EOF) || match(TokenType.CLOSED_CURLY)) {
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
                statements.add(parseKeyword());
            }
        }
        // Obradili smo čitav program:
        return statements;
    }

    private Node parseKeyword() {

        switch (peek().getTokenType()) {
            case PRINT:
                match(TokenType.PRINT);
                return parsePrint();
            case IF:
                // no match because of do-while block in parseIf()
                return parseIf();
            case BREAK:
                match(TokenType.BREAK);
                return parseBreak();
            case DO:
                match(TokenType.DO);
                return parseDo();
            case WHILE:
                match(TokenType.WHILE);
                return parseWhile();
            case FUNCTION:
                match(TokenType.FUNCTION);
                return parseFunction();
            default:
                throw new SyntaxException("Keyword not found?");
        }

    }

    private Node parseFunction() {
        if( peek().getTokenType() != TokenType.BASIC){
            throw new SyntaxException("Declaration type missing...");
        }
        Type type = (Type) pop().getValue();
        if( peek().getTokenType() != TokenType.IDENT){
            throw new SyntaxException("Declaration type missing...");
        }
        String fName = (String) pop().getValue();
        List<Node> parameters = new ArrayList<>();
        match(TokenType.OPEN_PARENTHESES);

        if(peek().getTokenType() != TokenType.CLOSED_PARENTHESES) {
            while (peek().getTokenType() == TokenType.BASIC) {
                List<Token> stmts = new ArrayList<>();
                Type varType = (Type) pop().getValue();

                if (peek().getTokenType() != TokenType.IDENT) {
                    throw new SyntaxException("Identifier was expected.");
                }
                stmts.add(pop());

                parameters.add(new DefStatement(stmts, varType));
                if (match(TokenType.COMMA)) {
                    continue;
                }
                break;
            }
        }
        match(TokenType.CLOSED_PARENTHESES);
        match(TokenType.OPEN_CURLY);
        List<Node> statements = parse();

        return new DefFunctionStatement(statements, parse(), type, fName);
    }


    private Node parseBreak() {
        return new BreakStatement();
    }


    private Node parseDo() {
        List<Node> statements;
        match(TokenType.OPEN_CURLY);
        statements = parse();
        match(TokenType.WHILE);
        match(TokenType.OPEN_PARENTHESES);
        NodeExpression cond = parseValue();
        match(TokenType.CLOSED_PARENTHESES);
        return new WhileStatement(statements, cond);
    }

    private Node parseWhile() {
        List<Node> statements;
        match(TokenType.OPEN_PARENTHESES);
        NodeExpression cond = parseValue();
        match(TokenType.CLOSED_PARENTHESES);
        match(TokenType.OPEN_CURLY);
        statements = parse();
        return new WhileStatement(statements, cond);
    }

    private Node parseIf() {
        List<Node> ifStatements = new ArrayList<>();
        boolean hasElse;
        do {
            pop();
            hasElse = false;
            List<Node> statements;
            match(TokenType.OPEN_PARENTHESES);
            NodeExpression cond = parseValue();
            match(TokenType.CLOSED_PARENTHESES);
            match(TokenType.OPEN_CURLY);
            statements = parse();
            ifStatements.add(new IfStatement(statements, cond));
            if (peek().getTokenType() == TokenType.ELSE) {
                pop();
                hasElse = true;
            }
        } while (peek().getTokenType() == TokenType.IF);

        if (hasElse) {
            match(TokenType.OPEN_CURLY);
            List<Node> statements = parse();
            ifStatements.add(new IfStatement(statements, new NodeConstant(Type.Bool, true)));
        }
        return new IfBlockStatement(ifStatements);
    }


    private Node parseDef() {
        if( peek().getTokenType() != TokenType.BASIC){
            throw new SyntaxException("Declaration type missing...");
        }
        Type type = (Type) pop().getValue();
        List<Token> variables = new ArrayList<>();
        while (true) {
            if (peek().getTokenType() != TokenType.IDENT) {
                throw new SyntaxException("Identifier was expected.");
            }
            variables.add(pop());
            if (match(TokenType.COMMA)) {
                continue;
            }
            break;
        }
        match(TokenType.SEMICOLON);
        return new DefStatement(variables, type);
    }


    private Node parseAsgnVal() {
        Token name = pop();
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
                x = new NodeExpressionMult(x, parseUnary());
            } else {
                pop();
                x = new NodeExpressionDiv(x, parseUnary());
            }
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
            case TRUE:
                pop();
                x = new NodeConstant(Type.Bool, true);
                return x;
            case FALSE:
                pop();
                x = new NodeConstant(Type.Bool, false);
                return x;
            case IDENT:
                String name = (String) pop().getValue();
                if(peek().getTokenType() == TokenType.OPEN_PARENTHESES){
                    pop();
                    return parseFuncCall(name);
                }
                return new NodeVariable(name);
            default:
                return x;
        }
    }

    private NodeExpression parseFuncCall(String name) {
        List<NodeExpression> vars = new ArrayList<>();
        if(peek().getTokenType() != TokenType.CLOSED_PARENTHESES) {
            while (true) {
                vars.add(factor());
                if(match(TokenType.COMMA)){
                    continue;
                }
                break;
            }
        }
        match(TokenType.CLOSED_PARENTHESES);
        return new NodeFunction(vars, name);
    }

    private NodeExpression createConstant() {
        if (peek().getValue() instanceof Integer) {
            return new NodeConstant(Type.Int, pop().getValue());
        } else if (peek().getValue() instanceof Double) {
            return new NodeConstant(Type.Double, pop().getValue());
        }
//        TODO
//        else if(peek().getValue() instanceof Char){
//            return new NodeConstant(Type.Char, pop().getValue());
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

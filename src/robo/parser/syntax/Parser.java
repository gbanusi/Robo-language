package robo.parser.syntax;

import robo.parser.execution.values.*;
import robo.parser.lexical.Token;
import robo.parser.lexical.TokenType;
import robo.parser.lexical.Tokenizer;
import robo.parser.lexical.Type;
import robo.parser.syntax.nodes.Node;
import robo.parser.syntax.nodes.ProgramNode;
import robo.parser.syntax.nodes.expression.*;
import robo.parser.syntax.nodes.statements.*;

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
                statements.add(parseAssignValue());
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
            case CONTINUE:
                match(TokenType.CONTINUE);
                return parseContinue();
            case DO:
                match(TokenType.DO);
                return parseDo();
            case RETURN:
                match(TokenType.RETURN);
                return parseReturn();
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

    private Node parseContinue() {
        if (!match(TokenType.SEMICOLON)) {
            throw new SyntaxException("Declaration type missing...");
        }
        return new ContinueStatement();
    }

    private Node parseReturn() {
        NodeExpression val = parseValue();
        match(TokenType.SEMICOLON);
        return new ReturnStatement(val);
    }

    private Node parseFunction() {
        if (peek().getTokenType() != TokenType.BASIC) {
            throw new SyntaxException("Declaration type missing...");
        }
        Type type = (Type) pop().getValue();
        if (peek().getTokenType() != TokenType.IDENT) {
            throw new SyntaxException("Declaration type missing...");
        }
        String fName = (String) pop().getValue();
        List<Node> parameters = new ArrayList<>();
        match(TokenType.OPEN_PARENTHESES);

        if (peek().getTokenType() != TokenType.CLOSED_PARENTHESES) {
            // TODO refaktorirati metodu na tri mjesta se pojavljuje
            while (peek().getTokenType() == TokenType.BASIC) {

                List<String> stmts = new ArrayList<>();
                Type varType = (Type) pop().getValue();

                boolean isConst = false;
                if (match(TokenType.CONST)) {
                    isConst = true;
                }

                if (peek().getTokenType() != TokenType.IDENT) {
                    throw new SyntaxException("Identifier was expected.");
                }
                stmts.add((String) pop().getValue());

                //TODO UBACITI DEFAULTNE VRIJEDNOSTI?
                parameters.add(new DefStatement(stmts, varType, isConst, new ArrayList<Node>()));
                if (match(TokenType.COMMA)) {
                    continue;
                }
                break;
            }
        }

        match(TokenType.CLOSED_PARENTHESES);
        match(TokenType.OPEN_CURLY);
        List<Node> statements = parse();

        return new DefFunctionStatement(statements, parameters, type, fName);
    }


    private Node parseBreak() {
        if (!match(TokenType.SEMICOLON)) {
            throw new SyntaxException("Declaration type missing...");
        }
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
        match(TokenType.SEMICOLON);
        return new DoStatement(statements, cond);
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
            ifStatements.add(new IfStatement(statements, new NodeConstant(Type.Bool, new RoboBool(true))));
        }
        return new IfBlockStatement(ifStatements);
    }


    private Node parseDef() {
        if (peek().getTokenType() != TokenType.BASIC) {
            throw new SyntaxException("Declaration type missing...");
        }
        Type type = (Type) pop().getValue();

        if (match(TokenType.OPEN_SQUARE)) {
            type = Type.Array;
            if (peek().getTokenType() != TokenType.CONSTANT) {
                throw new SyntaxException("Array definition must contain integer dimensions.");
            }
            List<NodeExpression> matrixDim = new ArrayList<>();
            while (true) {
                matrixDim.add(parseValue());
                if (!match(TokenType.SEMICOLON)) {
                    break;
                }
            }
            if(! match(TokenType.CLOSED_SQUARE)){
                throw new SyntaxException("Expected ']', got: '" + peek().getValue() + "', type: '" + peek().getTokenType() + "'.");
            }
        }

        boolean isConst = false;
        if (match(TokenType.CONST)) {
            isConst = true;
        }
        List<String> variables = new ArrayList<>();
        List<Node> assigns = new ArrayList<>();
        while (true) {
            if (peek().getTokenType() != TokenType.IDENT) {
                throw new SyntaxException("Identifier was expected, got: '" + peek().getValue() + "', type of: '" + peek().getTokenType() + ".");
            }
            variables.add((String) pop().getValue());
            if (match(TokenType.ASSIGN)) {
                assigns.add(new AssignVarStatement(variables.get(variables.size() - 1), parseValue()));
            }
            if (match(TokenType.COMMA)) {
                continue;
            }
            break;
        }
        if (!match(TokenType.SEMICOLON)) {
            throw new SyntaxException("Semicolon was expected instead of: '" + peek().getTokenType() + "'.");
        }
        return new DefStatement(variables, type, isConst, assigns);
    }

    private Node parsePrint() {
        List<NodeExpression> list = parseSeparateExpressions(TokenType.COMMA);
        if (!match(TokenType.SEMICOLON)) {
            throw new SyntaxException("Semicolon was expected.");
        }
        return new PrintStatement(list);
    }

    private Node parseAssignValue() {
        String name = (String) pop().getValue();
        if(match(TokenType.OPEN_SQUARE)){
            return parseAssignArrayIndex(name);
        } else {
            return parseAssignVariable(name);
        }
    }

    private Node parseAssignArrayIndex(String name) {
        List<NodeExpression> values = parseSeparateExpressions(TokenType.SEMICOLON);
        if (! match(TokenType.CLOSED_SQUARE)) {
            throw new SyntaxException("Closed square brackets expected, instead of: '" + peek().getTokenType() + "'!");
        }
        if (! match(TokenType.ASSIGN)) {
            throw new SyntaxException("Assign was expected.");
        }
        NodeExpression exp = parseValue();
        if (! match(TokenType.SEMICOLON)) {
            throw new SyntaxException("Semicolon was expected.");
        }
        return new AssignArrayStatement(name, values, exp);
    }

    private Node parseAssignVariable(String name) {
        if (!match(TokenType.ASSIGN)) {
            throw new SyntaxException("Assign was expected.");
        }
        NodeExpression exp = parseValue();
        if (!match(TokenType.SEMICOLON)) {
            throw new SyntaxException("Semicolon was expected.");
        }
        return new AssignVarStatement(name, exp);
    }


    private NodeExpression parseValue() {
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

    //  TODO NOT (!) TO ADD, check if factor() works instead of parseUnary()
    private NodeExpression parseUnary() {
        if (match(TokenType.UN_MINUS)) {
            return new NodeExpressionUnMinus(factor());
        } else if (match(TokenType.UN_REFERENCE)) {
            return new NodeExpressionUnReference(parseIdentFuncArray(true));
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
                List<NodeExpression> values = parseSeparateExpressions(TokenType.COMMA);
                if (!match(TokenType.CLOSED_SQUARE)) {
                    throw new SyntaxException("Closed square brackets expected!");
                }
                return new NodeArray(Type.Array, values);
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
            vars = parseSeparateExpressions(TokenType.COMMA);
            if (!match(TokenType.CLOSED_SQUARE)) {
                throw new SyntaxException("Closed square bracket expected!");
            }
            return new NodeArrayIndexing(name, vars);
        }
        return new NodeVariable(name);
    }


    private NodeExpression parseFuncCall(String name) {
        List<NodeExpression> vars = new ArrayList<>();
        if (!match(TokenType.CLOSED_PARENTHESES)) {
             vars = parseSeparateExpressions(TokenType.COMMA);
        }
        match(TokenType.CLOSED_PARENTHESES);
        return new NodeFunction(vars, name);
    }

    private List<NodeExpression> parseSeparateExpressions(TokenType sep) {
        List<NodeExpression> list = new ArrayList<>();
        while (true) {
            list.add(parseValue());
            if (!match(sep)) {
                break;
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

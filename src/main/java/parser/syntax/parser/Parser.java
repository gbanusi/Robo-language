package parser.syntax.parser;

import parser.lexical.Token;
import parser.lexical.TokenType;
import parser.lexical.Tokenizer;
import parser.syntax.SyntaxException;
import parser.syntax.nodes.Node;
import parser.syntax.nodes.ProgramNode;
import parser.syntax.nodes.statements.*;

import java.util.LinkedList;
import java.util.List;

public class Parser {


    private Tokenizer tokenizer;

    private ProgramNode programNode;

    private ParserHelper parserHelper;

    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
        this.parserHelper = new ParserHelper(tokenizer);
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


    public List<Node> parse() {
        List<Node> statements = new LinkedList<>();

        while (true) {
            // Ako je kraj programa, gotovi smo:
            if (match(TokenType.EOF) || match(TokenType.CLOSED_CURLY)) {
                break;
            }

            // Prema sintaksi može ići varijabla
            if (peek().getTokenType() == TokenType.IDENT) {
                statements.add(parseAssignValueOrFuncCall());
                continue;
            }

            // inicijalizacija varijable
            if (peek().getTokenType() == TokenType.BASIC) {
                statements.add(DefVarStatement.parseDef(parserHelper));
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
                return PrintStatement.parsePrint(parserHelper);
            case IF:
                return IfBlockStatement.parseIfBlock(parserHelper, this);
            case BREAK:
                return BreakStatement.parseBreak(parserHelper);
            case CONTINUE:
                return ContinueStatement.parseContinue(parserHelper);
            case DO:
                return DoStatement.parseDo(parserHelper, this);
            case RETURN:
                return ReturnStatement.parseReturn(parserHelper);
            case WHILE:
                return WhileStatement.parseWhile(parserHelper, this);
            case FUNCTION:
                return DefFunctionStatement.parseFunction(parserHelper, this);
            default:
                throw new SyntaxException("Keyword not found?");
        }

    }

    private Node parseAssignValueOrFuncCall() {
        String name = (String) pop().getValue();

        switch (peek().getTokenType()){
            case OPEN_SQUARE:
                return AssignArrayIndexStatement.parseAssignArrayIndex(name, parserHelper);
            case OPEN_PARENTHESES:
                // return parseFuncCall(name);
            default:
                return AssignVarStatement.parseAssignVariable(name, parserHelper);
        }
    }
}

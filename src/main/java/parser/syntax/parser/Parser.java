package parser.syntax.parser;

import parser.lexical.tokenizer.Token;
import parser.lexical.tokenizer.TokenType;
import parser.lexical.tokenizer.Tokenizer;
import parser.syntax.SyntaxException;
import parser.syntax.nodes.Node;
import parser.syntax.nodes.ProgramNode;
import parser.syntax.nodes.statements.assignation.AssignArrayIndexStatement;
import parser.syntax.nodes.statements.assignation.AssignVarStatement;
import parser.syntax.nodes.statements.definition.DefFunctionStatement;
import parser.syntax.nodes.statements.definition.DefVarStatement;
import parser.syntax.nodes.statements.function.ClassFunctionCallStatement;
import parser.syntax.nodes.statements.function.FunctionCallStatement;
import parser.syntax.nodes.statements.function.PrintStatement;
import parser.syntax.nodes.statements.keyword.IfBlockStatement;
import parser.syntax.nodes.statements.keyword.IncludeStatement;
import parser.syntax.nodes.statements.keyword.ReturnStatement;
import parser.syntax.nodes.statements.loop.LoopStatement;
import parser.syntax.nodes.statements.loop.extra.BreakStatement;
import parser.syntax.nodes.statements.loop.extra.ContinueStatement;

import java.util.ArrayList;
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
        //TODO-DEBUG remove
        System.out.println(tokenizer.getCurrentToken().getTokenType() + "  " + tokenizer.getCurrentToken().getValue().toString() );
        Token t = tokenizer.getCurrentToken();
        tokenizer.nextToken();
        return t;
    }


    public List<Node> parse() {
        ArrayList<Node> statements = new ArrayList<>();

        // include must be on top of document or function...
        while(peek().getTokenType() == TokenType.INCLUDE){
            statements.add(IncludeStatement.parseInclude(parserHelper));
        }

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
                throw new SyntaxException("Keyword expected instead of: " + peek().getTokenType() + "!");
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
            case LOOP:
                return LoopStatement.parseLoop(parserHelper, this);
            case RETURN:
                return ReturnStatement.parseReturn(parserHelper);
            case FUNCTION:
                return DefFunctionStatement.parseFunction(parserHelper, this);
            case INCLUDE:
                throw new SyntaxException("Keyword 'include' must be on top of file or function!" );
            default:
                throw new SyntaxException("Keyword not found..." );
        }

    }

    private Node parseAssignValueOrFuncCall() {
        String name = (String) pop().getValue();

        switch (peek().getTokenType()){
            case OPEN_SQUARE:
                return AssignArrayIndexStatement.parseAssignArrayIndex(name, parserHelper);
            case OPEN_PARENTHESES:
                return FunctionCallStatement.parseFunctionCall(parserHelper, name);
            case DOT:
                return ClassFunctionCallStatement.parseClassFunctionCall(parserHelper, name);
            default:
                return AssignVarStatement.parseAssignVariable(name, parserHelper);
        }
    }
}

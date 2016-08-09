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
        if(currToken().getTokenType() == t){
            tokenizer.nextToken();
            return true;
        }
        return false;
    }

    public Token currToken(){
        return tokenizer.getCurrentToken();
    }

    private ProgramNode parse() {
        List<Node> statements = new ArrayList<>();
        env = new VarEnvironment();

        while (true) {
            // Ako je kraj programa, gotovi smo:
            if (match(TokenType.EOF)) {
                break;
            }

            // Inače prema sintaksi mora doći ključna riječ:
            if (currToken().getTokenType() == TokenType.IDENT ) {
                if(VarEnvironment.get(currToken()) == null) {
                    throw new SyntaxException("Variable not declared!");
                } else {

                }
            }

            // Ako gledam naredbu "def":
            if ("def".equals(currToken().getValue())) {
                tokenizer.nextToken();
                statements.add(parseDef());
                continue;
            }

            // Ako gledam naredbu "let":
            if ("let".equals(currToken().getValue())) {
                tokenizer.nextToken();
                statements.add(parseLet());
                continue;
            }

            // Ako gledam naredbu "print":
            if ("print".equals(currToken().getValue())) {
                tokenizer.nextToken();
                statements.add(parsePrint());
                continue;
            }

            // Inače imam nepoznatu naredbu:
            throw new SyntaxException("Unexpected keyword found.");
        }
        // Obradili smo čitav program:
        return new ProgramNode(statements);
    }

    /**
     * Pomoćna metoda koja predstavlja implementaciju parsera naredbe "def".
     *
     * @return čvor koji predstavlja ovu naredbu
     */
    private Node parseDef() {
        List<String> variables = new ArrayList<>();
        while (true) {
            if (currToken().getTokenType() != TokenType.IDENT) {
                throw new SyntaxException("Identifier was expected.");
            }
            variables.add((String) currToken().getValue());
            tokenizer.nextToken();
            if (currToken().getTokenType() == TokenType.COMMA) {
                tokenizer.nextToken();
                continue;
            }
            break;
        }
        tokenizer.nextToken();
        if (currToken().getTokenType() != TokenType.KEYWORD) {
            throw new SyntaxException("A keyword was expected.");
        }
        String varType = (String) currToken().getValue();
        if (!"vector".equals(varType)) {
            throw new SyntaxException("Keyword 'vector' was expected.");
        }
        tokenizer.nextToken();
        if (currToken().getTokenType() != TokenType.SEMICOLON) {
            throw new SyntaxException("A semicolon was expected.");
        }
        tokenizer.nextToken();
//		return new DefStatement(variables, varType); TODO
        return null;
    }

    /**
     * Pomoćna metoda koja predstavlja implementaciju parsera naredbe "let".
     *
     * @return čvor koji predstavlja ovu naredbu
     */
    private Node parseLet() {
        if (currToken().getTokenType() != TokenType.IDENT) {
            throw new SyntaxException("Identifier was expected.");
        }
        String varName = (String) currToken().getValue();
        tokenizer.nextToken();
        if (currToken().getTokenType() != TokenType.ASSIGN) {
            throw new SyntaxException("Assignment was expected.");
        }
        tokenizer.nextToken();
        NodeExpression expr = parseExpression();
        if (currToken().getTokenType() != TokenType.SEMICOLON) {
            throw new SyntaxException("Semicolon was expected.");
        }
        tokenizer.nextToken();
        return new IfStatement(varName, expr);
    }

    /**
     * Pomoćna metoda koja predstavlja implementaciju parsera izraza
     * (ono što se nalazi s desne strane u naredbi pridruživanja ili
     * pak u naredbi print).
     *
     * @return čvor koji predstavlja čitav izraz
     */
    private NodeExpression parseExpression() {
        NodeExpression first = parseAtomicValue();
        while (true) {
            if (currToken().getTokenType() == TokenType.OP_PLUS) {
                tokenizer.nextToken();
                NodeExpression second = parseAtomicValue();
                first = new NodeExpressionAdd(first, second);
                continue;
            }
            if (currToken().getTokenType() == TokenType.OP_MINUS) {
                tokenizer.nextToken();
                NodeExpression second = parseAtomicValue();
                first = new NodeExpressionSub(first, second);
                continue;
            }
            break;
        }
        return first;
    }

    /**
     * Metoda koja parsira atomički izraz: to je vektorska konstanta, varijabla
     * ili pak podizraz u oblim zagradama.
     *
     * @return čvor koji predstavlja ovaj izraz
     */
    private NodeExpression parseAtomicValue() {
        if (currToken().getTokenType() == TokenType.IDENT) {
            String varName = (String) currToken().getValue();
            tokenizer.nextToken();
//			return new NodeVariable(varName); TODO
        }
        if (currToken().getTokenType() == TokenType.VECTOR_CONSTANT) {
            Vector vector = (Vector) currToken().getValue();
            tokenizer.nextToken();
//            return new NodeVector(vector); TODO
        }
        if (currToken().getTokenType() == TokenType.OPEN_PARENTHESES) {
            tokenizer.nextToken();
            NodeExpression expression = parseExpression();
            if (currToken().getTokenType() != TokenType.CLOSED_PARENTHESES) {
                throw new SyntaxException("Closed parentheses was expected.");
            }
            tokenizer.nextToken();
            return expression;
        }
        throw new SyntaxException("Unexpeced currToken type.");
    }

    /**
     * Implementacija parsera naredbe "print".
     *
     * @return čvor koji predstavlja ovu naredbu.
     */
    private Node parsePrint() {
        List<NodeExpression> list = new ArrayList<>();
        list.add(parseExpression());
        while (true) {
            if (currToken().getTokenType() != TokenType.COMMA) {
                break;
            }
            tokenizer.nextToken();
            list.add(parseExpression());
        }
        if (currToken().getTokenType() != TokenType.SEMICOLON) {
            throw new SyntaxException("Semicolon was expected.");
        }
        tokenizer.nextToken();
        return new PrintStatement(list);
    }
}

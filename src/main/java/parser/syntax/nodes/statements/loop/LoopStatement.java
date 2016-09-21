package parser.syntax.nodes.statements.loop;

import parser.execution.visitor.statement.NodeVisitor;
import parser.lexical.tokenizer.TokenType;
import parser.syntax.SyntaxException;
import parser.syntax.nodes.Node;
import parser.syntax.nodes.expression.NodeExpression;
import parser.syntax.nodes.statements.assignation.AssignVarStatement;
import parser.syntax.nodes.statements.definition.DefVarStatement;
import parser.syntax.parser.Parser;
import parser.syntax.parser.ParserHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregor on 15.08.16..
 */
public class LoopStatement extends Node implements ILoop{

    private Node varStmt;

    private NodeExpression condition;

    private Node step;

    private List<Node> statements = new ArrayList<>();

    private String varName;

    public LoopStatement(Node varStmt, NodeExpression condition, Node step, List<Node> statements, String varName) {
        this.varStmt = varStmt;
        this.condition = condition;
        this.step = step;
        this.statements = statements;
        this.varName = varName;
    }

    public Node getVarStmt() {
        return varStmt;
    }

    public NodeExpression getCondition() {
        return condition;
    }

    public Node getStep() {
        return step;
    }

    public List<Node> getStatements() {
        return statements;
    }

    public String getVarName() {
        return varName;
    }

    public static Node parseLoop(ParserHelper parserHelper, Parser parser) {
        if(parserHelper.peek().getTokenType().equals(TokenType.DO)){
            return DoStatement.parseDo(parserHelper, parser);
        } else {
            parserHelper.match(TokenType.LOOP);
            if(! parserHelper.match(TokenType.OPEN_PARENTHESES)){
                throw new SyntaxException("Loop must have conditions!");
            }

            if(! parserHelper.peek().getTokenType().equals(TokenType.BASIC)){
                NodeExpression condition = parserHelper.parseValue();
                parserHelper.match(TokenType.CLOSED_PARENTHESES);
                parserHelper.match(TokenType.OPEN_CURLY);
                List<Node> statements = parser.parse();
                return new WhileStatement(statements, condition);
            }

            if(parserHelper.peek().getTokenType().equals(TokenType.BASIC)){
                Node defVar = DefVarStatement.parseDef(parserHelper);
                NodeExpression condition = parserHelper.parseValue();
                if(! parserHelper.match(TokenType.SEMICOLON)){
                    throw new SyntaxException("Loop not good defined, ';' is missing after condition.");
                }
                if(! parserHelper.peek().getTokenType().equals(TokenType.IDENT)){
                    throw new SyntaxException("Loop not good defined, IDENT is missing after semicolon.");
                }
                String varName = (String) parserHelper.pop().getValue();
                if(! parserHelper.match(TokenType.ASSIGN)){
                    throw new SyntaxException("Loop not good defined, '=' is missing after condition.");
                }
                NodeExpression exp = parserHelper.parseValue();
                Node asgnStmt = new AssignVarStatement(varName, exp);

                parserHelper.match(TokenType.CLOSED_PARENTHESES);
                parserHelper.match(TokenType.OPEN_CURLY);
                List<Node> statements = parser.parse();

                return new LoopStatement(defVar, condition, asgnStmt, statements, varName);
            }

            throw new SyntaxException("Loop not good defined");
        }
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}

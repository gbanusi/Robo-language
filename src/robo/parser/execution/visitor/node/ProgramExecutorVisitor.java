package robo.parser.execution.visitor.node;

import robo.parser.execution.ExecutionException;
import robo.parser.execution.values.RoboBoolean;
import robo.parser.execution.values.RoboNull;
import robo.parser.execution.values.RoboValue;
import robo.parser.execution.visitor.expression.ExpressionEvalVisitor;
import robo.parser.syntax.nodes.Node;
import robo.parser.syntax.nodes.ProgramNode;
import robo.parser.syntax.nodes.expression.NodeExpression;
import robo.parser.syntax.nodes.statements.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by gregor on 15.08.16..
 */
public class ProgramExecutorVisitor implements NodeVisitor {

    private Map<String, RoboValue> vars = new HashMap<>();

    private Stack<LoopStatement> loops;


    public ProgramExecutorVisitor() {
        loops = new Stack<>();
    }

    @Override
    public void visit(AsgnValStatement node) {
        if (vars.containsKey(node.getVar())) {
            RoboValue val = calculateExpression(node.getExpression());
            vars.put(node.getVar(), val);
        } else {
            throw new ExecutionException("Variable " + node.getVar() + " not defined!");
        }
    }

    @Override
    public void visit(BreakStatement node) {
        loops.pop();
    }

    @Override
    public void visit(DefFunctionStatement node) {
        throw new ExecutionException("Function definition implemented currently...");
    }

    @Override
    public void visit(DefStatement node) {
        for (String s : node.getVariables()) {
            if (vars.containsKey(s)) {
                throw new ExecutionException("Variable " + s + " already defined!");
            }
            vars.put(s, new RoboNull());
        }
    }

    @Override
    public void visit(DoStatement node) {
        loops.add(node);
        RoboValue rb = calculateExpression(node.getExpression());
        if (!(rb instanceof RoboBoolean)) {
            throw new ExecutionException("Only boolean value in 'do-while' condition are allowed.");
        }
        boolean expr;
        LOOP:
        do {
            for (Node child : node.getStatements()) {
                child.accept(this);
                if (child instanceof BreakStatement) {
                    break LOOP;
                }
            }
            rb = calculateExpression(node.getExpression());
            expr = ((RoboBoolean) rb).getValue();
        } while (expr);
    }

    @Override
    public void visit(IfBlockStatement node) {
        for(Node child : node.getIfStatements()){
            IfStatement is = (IfStatement) child;
            RoboValue rb = calculateExpression(is.getExpression());
            boolean cond = ((RoboBoolean) rb).getValue();
            if(cond){
                is.accept(this);
                break;
            }
        }
    }

    @Override
    public void visit(IfStatement node) {
        for(Node child : node.getStatements()){
            child.accept(this);
        }
    }

    @Override
    public void visit(PrintStatement node) {
        for(NodeExpression ne : node.getExpressions()){
            RoboValue rb = calculateExpression(ne);
            System.out.print(rb + "  ");
        }
        System.out.print("\n");
    }

    @Override
    public void visit(ReturnStatement node) {
        throw new ExecutionException("Return not implemented currently...");
    }

    @Override
    public void visit(WhileStatement node) {
        loops.add(node);
        RoboValue rb = calculateExpression(node.getExpression());
        if (!(rb instanceof RoboBoolean)) {
            throw new ExecutionException("Only boolean value in 'do-while' condition are allowed.");
        }
        boolean expr = ((RoboBoolean) rb).getValue();
        LOOP:
        while (expr) {
            for (Node child : node.getStatements()) {
                child.accept(this);
                if (child instanceof BreakStatement) {
                    break LOOP;
                }
            }
            rb = calculateExpression(node.getExpression());
            expr = ((RoboBoolean) rb).getValue();
        }
    }

    @Override
    public void visit(ProgramNode node) {
        for(Node child : node.getStatements()){
            child.accept(this);
        }
    }

    private RoboValue calculateExpression(NodeExpression node) {
        ExpressionEvalVisitor visitor = new ExpressionEvalVisitor(vars);
        node.accept(visitor);
        return visitor.getResult();
    }
}

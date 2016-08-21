package robo.parser.execution.visitor.statement;

import robo.parser.execution.ExecEnvironment;
import robo.parser.execution.ExecutionException;
import robo.parser.execution.values.RoboBoolean;
import robo.parser.execution.values.RoboValue;
import robo.parser.execution.visitor.expression.ExpressionEvalVisitor;
import robo.parser.syntax.nodes.Node;
import robo.parser.syntax.nodes.ProgramNode;
import robo.parser.syntax.nodes.expression.NodeExpression;
import robo.parser.syntax.nodes.statements.*;

/**
 * Created by gregor on 15.08.16..
 */
public class ProgramStatementVisitor implements NodeVisitor {

    public ProgramStatementVisitor() {
    }

    @Override
    public void visit(AsgnValStatement node) {
        RoboValue val = calculateExpression(node.getExpression());
        ExecEnvironment.asgnVarValue(node.getVar(), val);
    }

    @Override
    public void visit(BreakStatement node) {
        ExecEnvironment.popLoop();
    }

    @Override
    public void visit(DefFunctionStatement node) {
        ExecEnvironment.declareFunc(node.getfName(), node);
    }

    @Override
    public void visit(DefStatement node) {
        if(! node.isConst()) {
            declareVar(node, false);
        } else {
            declareVar(node, true);
        }
    }

    private void declareVar(DefStatement node, boolean isConst) {
        node.getVariables().stream().forEach(var -> ExecEnvironment.declareVar(var, isConst));
    }

    @Override
    public void visit(DoStatement node) {
        ExecEnvironment.pushLoop(node);
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
        RoboValue rv = calculateExpression(node.getValue());
        ExecEnvironment.currentEnv().pushExpr(rv);
    }

    @Override
    public void visit(WhileStatement node) {
        ExecEnvironment.pushLoop(node);
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
        ExpressionEvalVisitor visitor = new ExpressionEvalVisitor(ExecEnvironment.currentEnv(), this);
        node.accept(visitor);
        return visitor.getResult();
    }
}

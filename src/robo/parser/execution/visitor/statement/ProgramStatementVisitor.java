package robo.parser.execution.visitor.statement;

import robo.parser.execution.ExecEnvironment;
import robo.parser.execution.ExecutionException;
import robo.parser.execution.values.RoboBool;
import robo.parser.execution.values.RoboNumeric;
import robo.parser.execution.values.RoboValue;
import robo.parser.execution.visitor.expression.ExpressionEvalVisitor;
import robo.parser.syntax.nodes.Node;
import robo.parser.syntax.nodes.ProgramNode;
import robo.parser.syntax.nodes.expression.NodeExpression;
import robo.parser.syntax.nodes.statements.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by gregor on 15.08.16..
 */
public class ProgramStatementVisitor implements NodeVisitor {

    public ProgramStatementVisitor() {
    }

    @Override
    public void visit(AssignVarStatement node) {
        RoboValue val = calculateExpression(node.getExpression());
        ExecEnvironment.asgnVarValue(node.getVar(), val);
    }

    @Override
    public void visit(BreakStatement node) {
        ExecEnvironment.popLoop();
    }

    @Override
    public void visit(ContinueStatement continueStatement) {
        // TODO refactor?
    }

    // TODO directly getting Array element by reference and changing the value, maybe sth better through ExecEnv?
    @Override
    public void visit(AssignArrayStatement assignArrayStatement) {
        List<RoboValue> rvList = new LinkedList<>();
        for(NodeExpression ne : assignArrayStatement.getIndexes()){
            RoboValue val = calculateExpression(ne);
            rvList.add(val);
        }
        RoboValue var = ExecEnvironment.getVarValue(assignArrayStatement.getVar()).index(rvList);
        RoboValue expression = calculateExpression(assignArrayStatement.getExpression());
        if(var.getType() == expression.getType() ||
                (var instanceof RoboNumeric && expression instanceof RoboNumeric)){
            var.setRoboValue(expression);
        }
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
        node.getAssigns().stream().forEach(asgn -> asgn.accept(this));
    }

    private void declareVar(DefStatement node, boolean isConst) {
        node.getVariables().stream().forEach(var -> ExecEnvironment.declareVar(var, isConst, node.getType()));
    }

    @Override
    public void visit(DoStatement node) {
        ExecEnvironment.pushLoop(node);
        RoboValue rb = calculateExpression(node.getExpression());
        if (!(rb instanceof RoboBool)) {
            throw new ExecutionException("Only boolean value in 'do-while' condition are allowed.");
        }
        boolean expr = true;
        LOOP:
        do {
            for (Node child : node.getStatements()) {
                child.accept(this);
                if (child instanceof BreakStatement) {
                    break LOOP;
                } else if (child instanceof ContinueStatement){
                    rb = calculateExpression(node.getExpression());
                    expr = ((RoboBool) rb).getValue();
                    if(expr) {
                        continue LOOP;
                    } else {
                        break LOOP;
                    }
                }
            }
            rb = calculateExpression(node.getExpression());
            expr = ((RoboBool) rb).getValue();
        } while (expr);
        ExecEnvironment.popLoop();
    }

    @Override
    public void visit(IfBlockStatement node) {
        for(Node child : node.getIfStatements()){
            IfStatement is = (IfStatement) child;
            RoboValue rb = calculateExpression(is.getExpression());
            boolean cond = ((RoboBool) rb).getValue();
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
        if (!(rb instanceof RoboBool)) {
            throw new ExecutionException("Only boolean value in 'do-while' condition are allowed.");
        }
        boolean expr = ((RoboBool) rb).getValue();
        LOOP:
        while (expr) {
            for (Node child : node.getStatements()) {
                child.accept(this);
                if (child instanceof BreakStatement) {
                    break LOOP;
                }
            }
            rb = calculateExpression(node.getExpression());
            expr = ((RoboBool) rb).getValue();
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

package parser.execution.visitor.statement;

import parser.execution.ExecutionException;
import parser.execution.environment.ExecutionEnv;
import parser.execution.values.RoboBool;
import parser.execution.values.RoboInteger;
import parser.execution.values.RoboNumeric;
import parser.execution.values.RoboValue;
import parser.execution.visitor.expression.ExpressionEvalVisitor;
import parser.lexical.Type;
import parser.syntax.SyntaxException;
import parser.syntax.nodes.Node;
import parser.syntax.nodes.ProgramNode;
import parser.syntax.nodes.expression.NodeExpression;
import parser.syntax.nodes.statements.*;

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
        ExecutionEnv.getExecEnv().defineVariable(node.getVar(), val, val.getType());
    }

    @Override
    public void visit(BreakStatement node) {
    }

    @Override
    public void visit(ContinueStatement continueStatement) {
        // TODO-2 refactor?
    }

    // TODO-1 directly getting Array element by reference and changing the value, maybe sth better through ExecEnv?
    @Override
    public void visit(AssignArrayIndexStatement assignArrayStatement) {
        List<RoboValue> rvList = new LinkedList<>();
        for (NodeExpression ne : assignArrayStatement.getIndexes()) {
            RoboValue val = calculateExpression(ne);
            rvList.add(val);
        }
        RoboValue var = ExecutionEnv.getExecEnv().getVariableValueByReference(assignArrayStatement.getVar()).index(rvList);
        RoboValue expression = calculateExpression(assignArrayStatement.getExpression());
        if (var.getType() == expression.getType() ||
                (var instanceof RoboNumeric && expression instanceof RoboNumeric)) {
            var.setRoboValue(expression);
        }
    }

    @Override
    public void visit(DefArrayType defArrayType) {
        // TODO-2 beautify
        RoboValue rows = calculateExpression(defArrayType.getMatrixDim().get(0));
        RoboValue cols = calculateExpression(defArrayType.getMatrixDim().get(1));
        if (!(rows instanceof RoboInteger) || !(cols instanceof RoboInteger)) {
            throw new SyntaxException("Column and row dimension must be integer number. E.g. [1, 2].");
        }
        // pushing the array type into the execution environment so anyone has access to it...
        if((Integer)rows.getValue() == 1){
            ExecutionEnv.createArrayType(defArrayType.getType(), (Integer)rows.getValue());
        } else if ((Integer)rows.getValue() > 1){
            ExecutionEnv.createMatrixType((Integer) rows.getValue(), (Integer) cols.getValue(), defArrayType.getType());
        }
    }

    @Override
    public void visit(DefFunctionStatement node) {
        ExecutionEnv.getExecEnv().declareFunc(node.getfName(), node);
    }

    @Override
    public void visit(DefVarStatement node) {
        if (!node.isConst()) {
            declareVar(node, false, node.getType());
        } else {
            declareVar(node, true, node.getType());
        }
        node.getAssigns().stream().forEach(asgn -> asgn.accept(this));
    }

    @Override
    public void visit(DefArrayStatement node) {
        node.getDefArrayType().accept(this);
        if (!node.isConst()) {
            declareVar(node, false, ExecutionEnv.getDefArrayType());
        } else {
            declareVar(node, true, ExecutionEnv.getDefArrayType());
        }
        node.getAssigns().stream().forEach(asgn -> asgn.accept(this));
    }



    private void declareVar(DefVarStatement node, boolean isConst, Type type) {
        node.getVariables().stream().forEach(var -> ExecutionEnv.getExecEnv().declareVariable(var, isConst, type));
    }

    @Override
    public void visit(DoStatement node) {
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
                } else if (child instanceof ContinueStatement) {
                    rb = calculateExpression(node.getExpression());
                    expr = ((RoboBool) rb).getValue();
                    if (expr) {
                        continue LOOP;
                    } else {
                        break LOOP;
                    }
                }
            }
            rb = calculateExpression(node.getExpression());
            expr = ((RoboBool) rb).getValue();
        } while (expr);
    }

    @Override
    public void visit(IfBlockStatement node) {
        for (Node child : node.getIfStatements()) {
            IfStatement is = (IfStatement) child;
            RoboValue rb = calculateExpression(is.getExpression());
            boolean cond = ((RoboBool) rb).getValue();
            if (cond) {
                is.accept(this);
                break;
            }
        }
    }

    @Override
    public void visit(IfStatement node) {
        for (Node child : node.getStatements()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(PrintStatement node) {
        for (NodeExpression ne : node.getExpressions()) {
            RoboValue rb = calculateExpression(ne);
            System.out.print(rb + "  ");
        }
        System.out.print("\n");
    }

    @Override
    public void visit(ReturnStatement node) {
        RoboValue rv = calculateExpression(node.getValue());
        ExecutionEnv.pushValue(rv);
    }

    @Override
    public void visit(WhileStatement node) {
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
        for (Node child : node.getStatements()) {
            child.accept(this);
        }
    }

    private RoboValue calculateExpression(NodeExpression node) {
        ExpressionEvalVisitor visitor = new ExpressionEvalVisitor(this);
        node.accept(visitor);
        return visitor.getResult();
    }
}

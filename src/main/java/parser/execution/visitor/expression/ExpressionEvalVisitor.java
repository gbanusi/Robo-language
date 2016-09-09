package parser.execution.visitor.expression;


import parser.execution.ExecutionException;
import parser.execution.environment.ExecutionEnv;
import parser.execution.values.RoboArray;
import parser.execution.values.RoboInteger;
import parser.execution.values.RoboValue;
import parser.execution.visitor.statement.ProgramStatementVisitor;
import parser.lexical.Type;
import parser.lexical.TypeArray;
import parser.lexical.TypeMatrix;
import parser.syntax.nodes.expression.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by gregor on 14.08.16..
 * TODO-1 left and right expression eval visitor?
 **/
public class ExpressionEvalVisitor implements ExpressionNodeVisitor {

    private boolean passByReference;

    private ProgramStatementVisitor programStatementVisitor;

    public ExpressionEvalVisitor(ProgramStatementVisitor programStatementVisitor) {
        this.programStatementVisitor = programStatementVisitor;
        passByReference = false;
    }

    @Override
    public void visit(NodeExpressionAdd add) {
        add.getFirst().accept(this);
        add.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpr();
        RoboValue left = ExecutionEnv.popExpr();
        ExecutionEnv.pushValue(left.add(right));
    }

    @Override
    public void visit(NodeExpressionSub sub) {
        sub.getFirst().accept(this);
        sub.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpr();
        RoboValue left = ExecutionEnv.popExpr();
        ExecutionEnv.pushValue(left.sub(right));
    }

    @Override
    public void visit(NodeExpressionMult mult) {
        mult.getFirst().accept(this);
        mult.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpr();
        RoboValue left = ExecutionEnv.popExpr();
        ExecutionEnv.pushValue(left.mult(right));
    }

    @Override
    public void visit(NodeExpressionDiv div) {
        div.getFirst().accept(this);
        div.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpr();
        RoboValue left = ExecutionEnv.popExpr();
        ExecutionEnv.pushValue(left.div(right));
    }

    @Override
    public void visit(NodeConstant nc) {
        ExecutionEnv.pushValue(nc.getValue());
    }

    @Override
    public void visit(NodeVariable nv) {
        if (!passByReference) {
            ExecutionEnv.pushValue(ExecutionEnv.getExecEnv().getVariableValue(nv.getVarName()));
        } else {
            passByReference = false;
            ExecutionEnv.pushValue(ExecutionEnv.getExecEnv().getVariableValueByReference((nv.getVarName())));
        }
    }


    @Override
    public void visit(NodeFunction nf) {
        ExecutionEnv.getExecEnv().executeFunc(nf, programStatementVisitor, this);
    }

    @Override
    public void visit(NodeExpressionAnd nea) {
        nea.getFirst().accept(this);
        nea.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpr();
        RoboValue left = ExecutionEnv.popExpr();
        ExecutionEnv.pushValue(left.and(right));
    }

    @Override
    public void visit(NodeExpressionOr neo) {
        neo.getFirst().accept(this);
        neo.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpr();
        RoboValue left = ExecutionEnv.popExpr();
        ExecutionEnv.pushValue(left.or(right));
    }

    @Override
    public void visit(NodeExpressionEquality nee) {
        nee.getFirst().accept(this);
        nee.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpr();
        RoboValue left = ExecutionEnv.popExpr();
        ExecutionEnv.pushValue(left.equal(right));
    }

    @Override
    public void visit(NodeExpressionNoEquality nen) {
        nen.getFirst().accept(this);
        nen.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpr();
        RoboValue left = ExecutionEnv.popExpr();
        ExecutionEnv.pushValue(left.notEqual(right));
    }

    @Override
    public void visit(NodeExpressionLTRelation ner) {
        ner.getFirst().accept(this);
        ner.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpr();
        RoboValue left = ExecutionEnv.popExpr();
        ExecutionEnv.pushValue(left.lowerThan(right));
    }

    @Override
    public void visit(NodeExpressionLERelation ner) {
        ner.getFirst().accept(this);
        ner.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpr();
        RoboValue left = ExecutionEnv.popExpr();
        ExecutionEnv.pushValue(left.lowerEqual(right));
    }

    @Override
    public void visit(NodeExpressionGERelation ner) {
        ner.getFirst().accept(this);
        ner.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpr();
        RoboValue left = ExecutionEnv.popExpr();
        ExecutionEnv.pushValue(left.greaterEqual(right));
    }

    @Override
    public void visit(NodeExpressionGTRelation ner) {
        ner.getFirst().accept(this);
        ner.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpr();
        RoboValue left = ExecutionEnv.popExpr();
        ExecutionEnv.pushValue(left.greaterThan(right));
    }

    @Override
    public void visit(NodeExpressionUnMinus neu) {
        neu.getFirst().accept(this);
        RoboValue val = ExecutionEnv.popExpr();
        ExecutionEnv.pushValue(val.unMinus());
    }

    @Override
    public void visit(NodeExpressionUnReference neu) {
        passByReference = true;
        neu.getFirst().accept(this);
        RoboValue val = ExecutionEnv.popExpr();
        if (passByReference) {
            throw new ExecutionException("Only variables and arrays can be passed by reference!");
        }
        ExecutionEnv.pushValue(val);
    }

    @Override
    public void visit(NodeArrayIndexing nodeArrayIndexing) {
        List<RoboValue> rvList = new LinkedList<>();
        for (NodeExpression ne : nodeArrayIndexing.getIndex()) {
            ne.accept(this);
            RoboValue val = ExecutionEnv.popExpr();
            if (!(val instanceof RoboInteger)) {
                // TODO-2 maknuti exception odavdje...
                throw new ExecutionException("Indexes can only be integers...");
            }
            rvList.add(val);
        }
        if (!passByReference) {
            RoboArray rv = (RoboArray) ExecutionEnv.getExecEnv().getVariableValue(nodeArrayIndexing.getVarName()).getValue();
            ExecutionEnv.pushValue(rv.index(rvList));
        } else {
            passByReference = false;
            RoboArray rv = (RoboArray) ExecutionEnv.getExecEnv().getVariableValueByReference(nodeArrayIndexing.getVarName()).getValue();
            ExecutionEnv.pushValue(rv.index(rvList));
        }

    }

    //  TODO-0  empty array supported?
    @Override
    public void visit(NodeArray nodeArray) {
        List<RoboValue> rvList = new LinkedList<>();
        Type arrayType = null;
        for (NodeExpression ne : nodeArray.getValue()) {
            ne.accept(this);
            RoboValue val = ExecutionEnv.popExpr();
            rvList.add(val);
            if(arrayType == null){
                arrayType = val.getType();
            } else if(( arrayType = Type.max(arrayType, val.getType()) ) == null){
                throw new ExecutionException("Array contains more different non numeric types...");
            }
        }
        ExecutionEnv.pushValue(new RoboArray(rvList, new TypeArray(arrayType, rvList.size())));
    }

    @Override
    public void visit(NodeMatrix nodeMatrix) {
        List<RoboValue> rvList = new LinkedList<>();
        TypeArray arrayType = null;
        Integer rows, cols = 0;
        for (NodeExpression ne : nodeMatrix.getValue()) {
            ne.accept(this);
            RoboValue val = ExecutionEnv.popExpr();
            rvList.add(val);
            TypeArray valType = (TypeArray) val.getType();
            if(arrayType == null){
                arrayType = valType;
                cols = arrayType.getLength();
            } else if(arrayType != valType){
                Type t = Type.max(arrayType.getType(), valType.getType());
                if(t != null){
                    arrayType.setType(t);
                } else {
                    throw new ExecutionException("Matrix contains more arrays of different types...");
                }
            }
        }
        rows = rvList.size();
        ExecutionEnv.pushValue(new RoboArray(rvList, new TypeMatrix(arrayType.getType(), rows, cols)));
    }

    @Override
    public void visit(NodeExpNot nen) {
        nen.getFirst().accept(this);
        RoboValue rv = ExecutionEnv.popExpr();
        ExecutionEnv.pushValue(rv.not());
    }

    public RoboValue getResult() {
        return ExecutionEnv.popExpr();
    }
}

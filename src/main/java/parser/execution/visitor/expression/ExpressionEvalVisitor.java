package parser.execution.visitor.expression;


import parser.execution.ExecutionException;
import parser.execution.environment.ExecutionEnv;
import parser.execution.values.*;
import parser.execution.visitor.TypeCheckingHelper;
import parser.execution.visitor.statement.ProgramStatementVisitor;
import parser.lexical.type.Type;
import parser.lexical.type.TypeArray;
import parser.lexical.type.TypeMatrix;
import parser.syntax.nodes.expression.*;
import parser.syntax.nodes.expression.comparation.*;
import parser.syntax.nodes.expression.logical.NodeExpressionAnd;
import parser.syntax.nodes.expression.logical.NodeExpressionNot;
import parser.syntax.nodes.expression.logical.NodeExpressionOr;
import parser.syntax.nodes.expression.operations.*;
import parser.syntax.nodes.expression.other.NodeExpressionUnReference;
import parser.syntax.nodes.expression.value.left.NodeArray;
import parser.syntax.nodes.expression.value.left.NodeArrayIndexing;
import parser.syntax.nodes.expression.value.left.NodeMatrix;
import parser.syntax.nodes.expression.value.left.NodeVariable;
import parser.syntax.nodes.expression.value.right.NodeConstant;
import parser.syntax.nodes.expression.value.right.NodeFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregor on 14.08.16..
 * TODO-1 left and right expression eval visitor?
 **/
public class ExpressionEvalVisitor implements ExpressionNodeVisitor {

    /**
     * Check whether a variable is passed by reference.
     */
    private boolean passByReference;

    private ProgramStatementVisitor programStatementVisitor;

    /**
     * Type checking variable.
     */
    private Type arrayElemType;

    public ExpressionEvalVisitor(ProgramStatementVisitor programStatementVisitor, Type type) {
        this.programStatementVisitor = programStatementVisitor;
        this.arrayElemType = type;
        passByReference = false;
    }

    public ExpressionEvalVisitor(ProgramStatementVisitor programStatementVisitor) {
        this.programStatementVisitor = programStatementVisitor;
        this.arrayElemType = null;
        passByReference = false;
    }

    @Override
    public void visit(NodeExpressionAdd add) {
        add.getFirst().accept(this);
        add.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpression();
        RoboValue left = ExecutionEnv.popExpression();
        RoboValue res = left.add(right);
        ExecutionEnv.pushExpression(res);
    }

    @Override
    public void visit(NodeExpressionSub sub) {
        sub.getFirst().accept(this);
        sub.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpression();
        RoboValue left = ExecutionEnv.popExpression();
        RoboValue res = left.sub(right);
        ExecutionEnv.pushExpression(res);
    }

    @Override
    public void visit(NodeExpressionMult mult) {
        mult.getFirst().accept(this);
        mult.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpression();
        RoboValue left = ExecutionEnv.popExpression();
        RoboValue res = left.mult(right);
        ExecutionEnv.pushExpression(res);
    }

    @Override
    public void visit(NodeExpressionDiv div) {
        div.getFirst().accept(this);
        div.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpression();
        RoboValue left = ExecutionEnv.popExpression();
        RoboValue res = left.div(right);
        ExecutionEnv.pushExpression(res);
    }

    @Override
    public void visit(NodeConstant nc) {
        RoboValue res = nc.getValue().duplicate();
        ExecutionEnv.pushExpression(res);
    }

    @Override
    public void visit(NodeVariable nv) {
        RoboValue res;
        if (!passByReference) {
            res = ExecutionEnv.getExecutionEnvironment().getVariableValue(nv.getVarName());
            ExecutionEnv.pushExpression(res);
        } else {
            res = ExecutionEnv.getExecutionEnvironment().getVariableValueByReference((nv.getVarName()));
            passByReference = false;
            ExecutionEnv.pushExpression(res);
        }
    }


    @Override
    public void visit(NodeFunction nf) {
        ExecutionEnv.getExecutionEnvironment().executeFunc(nf, programStatementVisitor, this);
    }

    @Override
    public void visit(NodeExpressionAnd nea) {
        nea.getFirst().accept(this);
        nea.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpression();
        RoboValue left = ExecutionEnv.popExpression();
        ExecutionEnv.pushExpression(left.and(right));
    }

    @Override
    public void visit(NodeExpressionOr neo) {
        neo.getFirst().accept(this);
        neo.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpression();
        RoboValue left = ExecutionEnv.popExpression();
        ExecutionEnv.pushExpression(left.or(right));
    }

    @Override
    public void visit(NodeExpressionEquality nee) {
        nee.getFirst().accept(this);
        nee.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpression();
        RoboValue left = ExecutionEnv.popExpression();
        ExecutionEnv.pushExpression(left.equal(right));
    }

    @Override
    public void visit(NodeExpressionNoEquality nen) {
        nen.getFirst().accept(this);
        nen.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpression();
        RoboValue left = ExecutionEnv.popExpression();
        ExecutionEnv.pushExpression(left.notEqual(right));
    }

    @Override
    public void visit(NodeExpressionLTRelation ner) {
        ner.getFirst().accept(this);
        ner.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpression();
        RoboValue left = ExecutionEnv.popExpression();
        ExecutionEnv.pushExpression(left.lowerThan(right));
    }

    @Override
    public void visit(NodeExpressionLERelation ner) {
        ner.getFirst().accept(this);
        ner.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpression();
        RoboValue left = ExecutionEnv.popExpression();
        ExecutionEnv.pushExpression(left.lowerEqual(right));
    }

    @Override
    public void visit(NodeExpressionGERelation ner) {
        ner.getFirst().accept(this);
        ner.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpression();
        RoboValue left = ExecutionEnv.popExpression();
        ExecutionEnv.pushExpression(left.greaterEqual(right));
    }

    @Override
    public void visit(NodeExpressionGTRelation ner) {
        ner.getFirst().accept(this);
        ner.getSecond().accept(this);
        RoboValue right = ExecutionEnv.popExpression();
        RoboValue left = ExecutionEnv.popExpression();
        ExecutionEnv.pushExpression(left.greaterThan(right));
    }

    @Override
    public void visit(NodeExpressionUnMinus neu) {
        neu.getFirst().accept(this);
        RoboValue val = ExecutionEnv.popExpression();
        ExecutionEnv.pushExpression(val.unMinus());
    }

    @Override
    public void visit(NodeExpressionUnReference neu) {
        passByReference = true;
        neu.getFirst().accept(this);
        RoboValue val = ExecutionEnv.popExpression();
        if (passByReference) {
            throw new ExecutionException("Only variables and arrays can be passed by reference!");
        }
        ExecutionEnv.pushExpression(val);
    }

    @Override
    public void visit(NodeArrayIndexing nodeArrayIndexing) {
        // TODO-1 cast into Integers indexes...
        List<RoboValue> rvList = new ArrayList<>();
        for (NodeExpression ne : nodeArrayIndexing.getIndex()) {
            ne.accept(this);
            RoboValue val = ExecutionEnv.popExpression();
            val = getVarRealValue(val);
            if (!(val instanceof RoboInteger)) {
                // TODO-2 maknuti exception odavdje...
                throw new ExecutionException("Indexes can only be integers, not '" + val.getType() + "'!");
            }
            rvList.add(val);
        }
        if (!passByReference) {
            RoboArrays rv = (RoboArrays) ExecutionEnv.getExecutionEnvironment().getVariableValue(nodeArrayIndexing.getVarName());
            ExecutionEnv.pushExpression(rv.index(rvList));
        } else {
            passByReference = false;
            RoboArrays rv = (RoboArrays) ExecutionEnv.getExecutionEnvironment().getVariableValueByReference(nodeArrayIndexing.getVarName());
            ExecutionEnv.pushExpression(rv.index(rvList));
        }

    }

    private RoboValue getVarRealValue(RoboValue val) {
        while (val instanceof RoboVariable) {
            val = (RoboValue) val.getValue();
        }
        return val;
    }

    //  TODO-0  empty array supported?
    @Override
    public void visit(NodeArray nodeArray) {
        List<RoboValue> rvList = new ArrayList<>();
        Type arrayType = null;
        for (NodeExpression ne : nodeArray.getValue()) {
            ne.accept(this);
            RoboValue val = ExecutionEnv.popExpression();
            rvList.add(TypeCheckingHelper.convertType(val, arrayElemType));
        }
        cols = rvList.size();
        ExecutionEnv.pushExpression(new RoboArray(rvList, new TypeArray(arrayElemType, rvList.size())));
    }

    /**
     * Used when matrix generated.
     */
    private Integer cols;

    @Override
    public void visit(NodeMatrix nodeMatrix) {
        List<RoboValue> rvList = new ArrayList<>();
        TypeArray arrayType = null;
        Integer rows;
        cols = 0;
        for (NodeExpression ne : nodeMatrix.getValue()) {
            ne.accept(this);
            RoboValue val = ExecutionEnv.popExpression();
            rvList.add(val);
        }
        rows = rvList.size();
        ExecutionEnv.pushExpression(new RoboMatrix(rvList, new TypeMatrix(arrayElemType, rows, cols)));
    }

    @Override
    public void visit(NodeExpressionDot ned) {
        ned.getFirst().accept(this);
        RoboValue obj = ExecutionEnv.popExpression();
        if(!(obj instanceof RoboObject)){
            throw new ExecutionException("Cannot call '.' operator on non class object");
        }
        NodeFunction nf = (NodeFunction) ned.getSecond();
        RoboObject ro = (RoboObject) obj;
        ro.callFunction(nf, this);
    }

    @Override
    public void visit(NodeExpressionNot nen) {
        nen.getFirst().accept(this);
        RoboValue rv = ExecutionEnv.popExpression();
        ExecutionEnv.pushExpression(rv.not());
    }


    public RoboValue getResult() {
        return ExecutionEnv.popExpression();
    }

}

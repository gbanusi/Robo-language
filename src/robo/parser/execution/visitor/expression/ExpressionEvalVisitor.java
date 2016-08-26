package robo.parser.execution.visitor.expression;

import robo.parser.execution.ExecEnvironment;
import robo.parser.execution.ExecutionException;
import robo.parser.execution.values.RoboArray;
import robo.parser.execution.values.RoboInteger;
import robo.parser.execution.values.RoboReference;
import robo.parser.execution.values.RoboValue;
import robo.parser.execution.visitor.statement.ProgramStatementVisitor;
import robo.parser.syntax.nodes.expression.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by gregor on 14.08.16..
 *
 * WARNING: everything is implemented so that it push new value on stack so no
 * object reference can get between.
 * TODO solve above warning.
 */
public class ExpressionEvalVisitor implements ExpressionNodeVisitor {

    private ExecEnvironment execEnvironment;

    private ProgramStatementVisitor programStatementVisitor;

    public ExpressionEvalVisitor(ExecEnvironment execEnvironment, ProgramStatementVisitor programStatementVisitor) {
        this.execEnvironment = execEnvironment;
        this.programStatementVisitor = programStatementVisitor;
    }

    @Override
    public void visit(NodeExpressionAdd add) {
        add.getFirst().accept(this);
        add.getSecond().accept(this);
        RoboValue right = execEnvironment.popExpr();
        RoboValue left = execEnvironment.popExpr();
        execEnvironment.pushExpr(left.add(right));
    }

    @Override
    public void visit(NodeExpressionSub sub) {
        sub.getFirst().accept(this);
        sub.getSecond().accept(this);
        RoboValue right = execEnvironment.popExpr();
        RoboValue left = execEnvironment.popExpr();
        execEnvironment.pushExpr(left.sub(right));
    }

    @Override
    public void visit(NodeExpressionMult mult) {
        mult.getFirst().accept(this);
        mult.getSecond().accept(this);
        RoboValue right = execEnvironment.popExpr();
        RoboValue left = execEnvironment.popExpr();
        execEnvironment.pushExpr(left.mult(right));
    }

    @Override
    public void visit(NodeExpressionDiv div) {
        div.getFirst().accept(this);
        div.getSecond().accept(this);
        RoboValue right = execEnvironment.popExpr();
        RoboValue left = execEnvironment.popExpr();
        execEnvironment.pushExpr(left.div(right));
    }

    @Override
    public void visit(NodeConstant nc) {
        execEnvironment.pushExpr(nc.getValue());
    }

    // TODO returning reference, have to implement factory or sth...
    @Override
    public void visit(NodeVariable nv) {
        execEnvironment.pushExpr(ExecEnvironment.getVarValue(nv.getVarName()));
    }



    @Override
    public void visit(NodeFunction nf) {
        ExecEnvironment.executeFunc(nf, programStatementVisitor, this);
    }

    @Override
    public void visit(NodeExpressionAnd nea) {
        nea.getFirst().accept(this);
        nea.getSecond().accept(this);
        RoboValue right = execEnvironment.popExpr();
        RoboValue left = execEnvironment.popExpr();
        execEnvironment.pushExpr(left.and(right));
    }

    @Override
    public void visit(NodeExpressionOr neo) {
        neo.getFirst().accept(this);
        neo.getSecond().accept(this);
        RoboValue right = execEnvironment.popExpr();
        RoboValue left = execEnvironment.popExpr();
        execEnvironment.pushExpr(left.or(right));
    }

    @Override
    public void visit(NodeExpressionEquality nee) {
        nee.getFirst().accept(this);
        nee.getSecond().accept(this);
        RoboValue right = execEnvironment.popExpr();
        RoboValue left = execEnvironment.popExpr();
        execEnvironment.pushExpr(left.equal(right));
    }

    @Override
    public void visit(NodeExpressionNoEquality nen) {
        nen.getFirst().accept(this);
        nen.getSecond().accept(this);
        RoboValue right = execEnvironment.popExpr();
        RoboValue left = execEnvironment.popExpr();
        execEnvironment.pushExpr(left.notEqual(right));
    }

    @Override
    public void visit(NodeExpressionLTRelation ner) {
        ner.getFirst().accept(this);
        ner.getSecond().accept(this);
        RoboValue right = execEnvironment.popExpr();
        RoboValue left = execEnvironment.popExpr();
        execEnvironment.pushExpr(left.lowerThan(right));
    }

    @Override
    public void visit(NodeExpressionLERelation ner) {
        ner.getFirst().accept(this);
        ner.getSecond().accept(this);
        RoboValue right = execEnvironment.popExpr();
        RoboValue left = execEnvironment.popExpr();
        execEnvironment.pushExpr(left.lowerEqual(right));
    }

    @Override
    public void visit(NodeExpressionGERelation ner) {
        ner.getFirst().accept(this);
        ner.getSecond().accept(this);
        RoboValue right = execEnvironment.popExpr();
        RoboValue left = execEnvironment.popExpr();
        execEnvironment.pushExpr(left.greaterEqual(right));
    }

    @Override
    public void visit(NodeExpressionGTRelation ner) {
        ner.getFirst().accept(this);
        ner.getSecond().accept(this);
        RoboValue right = execEnvironment.popExpr();
        RoboValue left = execEnvironment.popExpr();
        execEnvironment.pushExpr(left.greaterThan(right));
    }

    @Override
    public void visit(NodeExpressionUnMinus neu) {
        neu.getFirst().accept(this);
        RoboValue val = execEnvironment.popExpr();
        execEnvironment.pushExpr(val.unMinus());
    }

    @Override
    public void visit(NodeExpressionUnReference neu) {
        neu.getFirst().accept(this);
        RoboValue val = execEnvironment.popExpr();
        RoboValue refer = new RoboReference(val, neu.getVarName());
        execEnvironment.pushExpr(refer);
    }

    @Override
    public void visit(NodeArrayIndexing nodeArrayIndexing) {
        List<RoboValue> rvList = new LinkedList<>();
        for(NodeExpression ne : nodeArrayIndexing.getIndex()){
            ne.accept(this);
            RoboValue val = execEnvironment.popExpr();
            if(! (val instanceof RoboInteger)){
                // TODO maknuti exception odavdje...
                throw new ExecutionException("Indexes can only be integers...");
            }
            rvList.add(val);
        }
        RoboArray rv = (RoboArray) ExecEnvironment.getVarValue(nodeArrayIndexing.getVarName());
        execEnvironment.pushExpr(rv.index(rvList));
    }

    @Override
    public void visit(NodeArray nodeArray) {
        List<RoboValue> rvList = new LinkedList<>();
        for(NodeExpression ne : nodeArray.getValue()){
            ne.accept(this);
            RoboValue val = execEnvironment.popExpr();
            rvList.add(val);
        }
        RoboValue array = new RoboArray(rvList);
        execEnvironment.pushExpr(array);
    }

    public RoboValue getResult(){
        if(execEnvironment.sizeExpr() != 1) {
            throw new ExecutionException(
                    "Evaluation of expression failed!");
        }
        return execEnvironment.popExpr();
    }
}

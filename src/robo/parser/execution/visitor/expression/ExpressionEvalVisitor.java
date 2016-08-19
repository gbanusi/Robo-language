package robo.parser.execution.visitor.expression;

import robo.parser.execution.ExecEnv;
import robo.parser.execution.ExecutionException;
import robo.parser.execution.values.RoboValue;
import robo.parser.execution.visitor.node.ProgramExecutorVisitor;
import robo.parser.syntax.SyntaxException;
import robo.parser.syntax.nodes.expression.*;

/**
 * Created by gregor on 14.08.16..
 */
public class ExpressionEvalVisitor implements ExpressionNodeVisitor {

    private ExecEnv execEnv;

    private ProgramExecutorVisitor programExecutorVisitor;

    public ExpressionEvalVisitor(ExecEnv execEnv, ProgramExecutorVisitor programExecutorVisitor) {
        this.execEnv = execEnv;
        this.programExecutorVisitor = programExecutorVisitor;
    }

    @Override
    public void visit(NodeExpressionAdd add) {
        add.getFirst().accept(this);
        add.getSecond().accept(this);
        RoboValue right = execEnv.popExpr();
        RoboValue left = execEnv.popExpr();
        execEnv.pushExpr(left.add(right));
    }

    @Override
    public void visit(NodeExpressionSub sub) {
        sub.getFirst().accept(this);
        sub.getSecond().accept(this);
        RoboValue right = execEnv.popExpr();
        RoboValue left = execEnv.popExpr();
        execEnv.pushExpr(left.sub(right));
    }

    @Override
    public void visit(NodeExpressionMult mult) {
        mult.getFirst().accept(this);
        mult.getSecond().accept(this);
        RoboValue right = execEnv.popExpr();
        RoboValue left = execEnv.popExpr();
        execEnv.pushExpr(left.mult(right));
    }

    @Override
    public void visit(NodeExpressionDiv div) {
        div.getFirst().accept(this);
        div.getSecond().accept(this);
        RoboValue right = execEnv.popExpr();
        RoboValue left = execEnv.popExpr();
        execEnv.pushExpr(left.div(right));
    }

    @Override
    public void visit(NodeConstant nc) {
        execEnv.pushExpr(nc.getValue());
    }

    @Override
    public void visit(NodeVariable nv) {
        execEnv.pushExpr(ExecEnv.getVarValue(nv.getVarName()));
    }

    @Override
    public void visit(NodeVector nv) {
        throw new SyntaxException("Vector type not supported currently..");
    }

    @Override
    public void visit(NodeFunction nf) {
        ExecEnv.executeFunc(nf, programExecutorVisitor);
    }

    @Override
    public void visit(NodeExpressionAnd nea) {
        nea.getFirst().accept(this);
        nea.getSecond().accept(this);
        RoboValue right = execEnv.popExpr();
        RoboValue left = execEnv.popExpr();
        execEnv.pushExpr(left.and(right));
    }

    @Override
    public void visit(NodeExpressionOr neo) {
        neo.getFirst().accept(this);
        neo.getSecond().accept(this);
        RoboValue right = execEnv.popExpr();
        RoboValue left = execEnv.popExpr();
        execEnv.pushExpr(left.or(right));
    }

    @Override
    public void visit(NodeExpressionEquality nee) {
        nee.getFirst().accept(this);
        nee.getSecond().accept(this);
        RoboValue right = execEnv.popExpr();
        RoboValue left = execEnv.popExpr();
        execEnv.pushExpr(left.equal(right));
    }

    @Override
    public void visit(NodeExpressionNoEquality nen) {
        nen.getFirst().accept(this);
        nen.getSecond().accept(this);
        RoboValue right = execEnv.popExpr();
        RoboValue left = execEnv.popExpr();
        execEnv.pushExpr(left.notEqual(right));
    }

    @Override
    public void visit(NodeExpressionLTRelation ner) {
        ner.getFirst().accept(this);
        ner.getSecond().accept(this);
        RoboValue right = execEnv.popExpr();
        RoboValue left = execEnv.popExpr();
        execEnv.pushExpr(left.lowerThan(right));
    }

    @Override
    public void visit(NodeExpressionLERelation ner) {
        ner.getFirst().accept(this);
        ner.getSecond().accept(this);
        RoboValue right = execEnv.popExpr();
        RoboValue left = execEnv.popExpr();
        execEnv.pushExpr(left.lowerEqual(right));
    }

    @Override
    public void visit(NodeExpressionGERelation ner) {
        ner.getFirst().accept(this);
        ner.getSecond().accept(this);
        RoboValue right = execEnv.popExpr();
        RoboValue left = execEnv.popExpr();
        execEnv.pushExpr(left.greaterEqual(right));
    }

    @Override
    public void visit(NodeExpressionGTRelation ner) {
        ner.getFirst().accept(this);
        ner.getSecond().accept(this);
        RoboValue right = execEnv.popExpr();
        RoboValue left = execEnv.popExpr();
        execEnv.pushExpr(left.greaterThan(right));
    }

    @Override
    public void visit(NodeExpressionUnMinus neu) {
        neu.getFirst().accept(this);
        RoboValue val = execEnv.popExpr();
        execEnv.pushExpr(val.unMinus());
    }

    public RoboValue getResult(){
        if(execEnv.sizeExpr() != 1) {
            throw new ExecutionException(
                    "Evaluation of expression failed!");
        }
        return execEnv.popExpr();
    }
}

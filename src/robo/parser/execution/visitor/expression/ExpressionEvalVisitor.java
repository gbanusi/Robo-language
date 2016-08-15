package robo.parser.execution.visitor.expression;

import robo.parser.execution.ExecutionException;
import robo.parser.execution.values.RoboValue;
import robo.parser.syntax.SyntaxException;
import robo.parser.syntax.nodes.expression.NodeConstant;
import robo.parser.syntax.nodes.expression.NodeFunction;
import robo.parser.syntax.nodes.expression.NodeVariable;
import robo.parser.syntax.nodes.expression.NodeVector;
import robo.parser.syntax.nodes.expression.*;

import java.util.Map;
import java.util.Stack;

/**
 * Created by gregor on 14.08.16..
 */
public class ExpressionEvalVisitor implements ExpressionNodeVisitor {

    private Stack<RoboValue> stack = new Stack<>();

    private Map<String, RoboValue> vars;

    public ExpressionEvalVisitor(Map<String, RoboValue> vars) {
        this.vars = vars;
    }

    @Override
    public void visit(NodeExpressionAdd add) {
        add.getFirst().accept(this);
        add.getSecond().accept(this);
        RoboValue right = stack.pop();
        RoboValue left = stack.pop();
        stack.push(left.add(right));
    }

    @Override
    public void visit(NodeExpressionSub sub) {
        sub.getFirst().accept(this);
        sub.getSecond().accept(this);
        RoboValue right = stack.pop();
        RoboValue left = stack.pop();
        stack.push(left.sub(right));
    }

    @Override
    public void visit(NodeExpressionMult mult) {
        mult.getFirst().accept(this);
        mult.getSecond().accept(this);
        RoboValue right = stack.pop();
        RoboValue left = stack.pop();
        stack.push(left.mult(right));
    }

    @Override
    public void visit(NodeExpressionDiv div) {
        div.getFirst().accept(this);
        div.getSecond().accept(this);
        RoboValue right = stack.pop();
        RoboValue left = stack.pop();
        stack.push(left.div(right));
    }

    @Override
    public void visit(NodeConstant nc) {
        stack.push(nc.getValue());
    }

    @Override
    public void visit(NodeVariable nv) {
        stack.push(vars.get(nv.getVarName()));
    }

    @Override
    public void visit(NodeVector nv) {
        throw new SyntaxException("Vector type not supported currently");
    }

    @Override
    public void visit(NodeFunction nf) {
        throw new SyntaxException("Vector type not supported currently");
    }

    @Override
    public void visit(NodeExpressionAnd nea) {
        nea.getFirst().accept(this);
        nea.getSecond().accept(this);
        RoboValue right = stack.pop();
        RoboValue left = stack.pop();
        stack.push(left.and(right));
    }

    @Override
    public void visit(NodeExpressionOr neo) {
        neo.getFirst().accept(this);
        neo.getSecond().accept(this);
        RoboValue right = stack.pop();
        RoboValue left = stack.pop();
        stack.push(left.or(right));
    }

    @Override
    public void visit(NodeExpressionEquality nee) {
        nee.getFirst().accept(this);
        nee.getSecond().accept(this);
        RoboValue right = stack.pop();
        RoboValue left = stack.pop();
        stack.push(left.equal(right));
    }

    @Override
    public void visit(NodeExpressionNoEquality nen) {
        nen.getFirst().accept(this);
        nen.getSecond().accept(this);
        RoboValue right = stack.pop();
        RoboValue left = stack.pop();
        stack.push( left.notEqual(right) );
    }

    @Override
    public void visit(NodeExpressionLTRelation ner) {
        ner.getFirst().accept(this);
        ner.getSecond().accept(this);
        RoboValue right = stack.pop();
        RoboValue left = stack.pop();
        stack.push( left.lowerThan(right) );
    }

    @Override
    public void visit(NodeExpressionLERelation ner) {
        ner.getFirst().accept(this);
        ner.getSecond().accept(this);
        RoboValue right = stack.pop();
        RoboValue left = stack.pop();
        stack.push( left.lowerEqual(right) );
    }

    @Override
    public void visit(NodeExpressionGERelation ner) {
        ner.getFirst().accept(this);
        ner.getSecond().accept(this);
        RoboValue right = stack.pop();
        RoboValue left = stack.pop();
        stack.push( left.greaterEqual(right) );
    }

    @Override
    public void visit(NodeExpressionGTRelation ner) {
        ner.getFirst().accept(this);
        ner.getSecond().accept(this);
        RoboValue right = stack.pop();
        RoboValue left = stack.pop();
        stack.push( left.greaterThan(right) );
    }

    @Override
    public void visit(NodeExpressionUnMinus neu) {
        neu.getFirst().accept(this);
        RoboValue val = stack.pop();
        stack.push( val.unMinus() );
    }

    public RoboValue getResult(){
        if(stack.size() != 1) {
            throw new ExecutionException(
                    "Evaluation of expression failed!");
        }
        return stack.peek();
    }
}

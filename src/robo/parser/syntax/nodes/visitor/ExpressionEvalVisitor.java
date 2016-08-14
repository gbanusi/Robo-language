package robo.parser.syntax.nodes.visitor;

import robo.parser.lexical.Type;
import robo.parser.syntax.SyntaxException;
import robo.parser.syntax.nodes.value.NodeConstant;
import robo.parser.syntax.nodes.value.NodeFunction;
import robo.parser.syntax.nodes.value.NodeVariable;
import robo.parser.syntax.nodes.value.NodeVector;
import robo.parser.syntax.nodes.value.expression.*;

import java.util.Map;
import java.util.Stack;

/**
 * Created by gregor on 14.08.16..
 */
public class ExpressionEvalVisitor implements ExpressionNodeVisitor {

    private Stack<Double> numStack = new Stack<>();

    private Stack<Boolean> boolStack = new Stack<>();

    private Map<String, Double> numVars;

    private Map<String, Boolean> boolVars;

    public ExpressionEvalVisitor(Map<String, Double> numVars, Map<String, Boolean> boolVars) {
        this.numVars = numVars;
        this.boolVars = boolVars;
    }

    @Override
    public void visit(NodeExpressionAdd add) {
        add.getFirst().accept(this);
        add.getSecond().accept(this);
        Double right = numStack.pop();
        Double left = numStack.pop();
        numStack.push(left + right);
    }

    @Override
    public void visit(NodeExpressionSub sub) {
        sub.getFirst().accept(this);
        sub.getSecond().accept(this);
        Double right = numStack.pop();
        Double left = numStack.pop();
        numStack.push(left - right);
    }

    @Override
    public void visit(NodeExpressionMult mult) {
        mult.getFirst().accept(this);
        mult.getSecond().accept(this);
        Double right = numStack.pop();
        Double left = numStack.pop();
        numStack.push(left * right);
    }

    @Override
    public void visit(NodeExpressionDiv div) {
        div.getFirst().accept(this);
        div.getSecond().accept(this);
        Double right = numStack.pop();
        Double left = numStack.pop();
        numStack.push(left / right);
    }

    @Override
    public void visit(NodeConstant nc) {
        if (nc.getType() == Type.Double || nc.getType() == Type.Int) {
            numStack.push((Double) nc.getValue());
        } else if (nc.getType() == Type.Bool) {
            boolStack.push((Boolean) nc.getValue());
        } else {
            throw new SyntaxException("Only double, int and bool types supported currently");
        }
    }

    @Override
    public void visit(NodeVariable nv) {
        if(numVars.containsKey(nv.getVarName())){
            numStack.push(numVars.get(nv.getVarName()));
        } else if (boolVars.containsKey(nv.getVarName())){
            boolStack.push(boolVars.get(nv.getVarName()));
        } else {
            throw new SyntaxException("Only double, int and bool types supported currently");
        }
    }

    @Override
    public void visit(NodeVector nv) {
        throw new SyntaxException("Vector type not supported currently");
    }

    @Override
    public void visit(NodeFunction nf) {

    }

    @Override
    public void visit(NodeExpressionAnd nea) {

    }

    @Override
    public void visit(NodeExpressionOr neo) {

    }

    @Override
    public void visit(NodeExpressionEquality nee) {

    }

    @Override
    public void visit(NodeExpressionNoEquality nen) {

    }

    @Override
    public void visit(NodeExpressionLTRelation ner) {

    }

    @Override
    public void visit(NodeExpressionLERelation ner) {

    }

    @Override
    public void visit(NodeExpressionGERelation ner) {

    }

    @Override
    public void visit(NodeExpressionGTRelation ner) {

    }

    @Override
    public void visit(NodeExpressionUnMinus neu) {

    }
}

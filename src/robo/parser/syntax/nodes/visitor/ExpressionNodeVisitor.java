package robo.parser.syntax.nodes.visitor;

import robo.parser.syntax.nodes.value.NodeConstant;
import robo.parser.syntax.nodes.value.NodeFunction;
import robo.parser.syntax.nodes.value.NodeVariable;
import robo.parser.syntax.nodes.value.NodeVector;
import robo.parser.syntax.nodes.value.expression.*;

/**
 * Created by gregor on 14.08.16..
 */
public interface ExpressionNodeVisitor {

    // OPERATIONS
    public void visit(NodeExpressionAdd nea);

    public void visit(NodeExpressionSub nes);

    public void visit(NodeExpressionMult nem);

    public void visit(NodeExpressionDiv ned);

    // VALUE HOLDERS
    public void visit(NodeConstant nc);

    public void visit(NodeVariable nv);

    public void visit(NodeVector nv);

    public void visit(NodeFunction nf);

    // LOGICAL
    public void visit(NodeExpressionAnd nea);

    public void visit(NodeExpressionOr neo);

    // RELATIONS
    public void visit(NodeExpressionEquality nee);

    public void visit(NodeExpressionNoEquality nen);

    public void visit(NodeExpressionLTRelation ner);

    public void visit(NodeExpressionLERelation ner);

    public void visit(NodeExpressionGERelation ner);

    public void visit(NodeExpressionGTRelation ner);

    public void visit(NodeExpressionUnMinus neu);

}

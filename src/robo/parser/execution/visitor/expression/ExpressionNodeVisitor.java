package robo.parser.execution.visitor.expression;

import robo.parser.syntax.nodes.expression.*;

/**
 * Created by gregor on 14.08.16..
 */
public interface ExpressionNodeVisitor {

    // OPERATIONS
    void visit(NodeExpressionAdd nea);

    void visit(NodeExpressionSub nes);

    void visit(NodeExpressionMult nem);

    void visit(NodeExpressionDiv ned);

    // VALUE HOLDERS
    void visit(NodeConstant nc);

    void visit(NodeVariable nv);

    void visit(NodeFunction nf);

    // LOGICAL
    void visit(NodeExpressionAnd nea);

    void visit(NodeExpressionOr neo);

    // RELATIONS
    void visit(NodeExpressionEquality nee);

    void visit(NodeExpressionNoEquality nen);

    void visit(NodeExpressionLTRelation ner);

    void visit(NodeExpressionLERelation ner);

    void visit(NodeExpressionGERelation ner);

    void visit(NodeExpressionGTRelation ner);

    void visit(NodeExpressionUnMinus neu);

    void visit(NodeExpressionUnReference neu);

    void visit(NodeArrayIndexing nodeArrayIndexing);

    void visit(NodeArray nodeArray);
}
